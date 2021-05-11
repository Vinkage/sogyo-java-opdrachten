package nl.sogyo.javaopdrachten.decisiontree;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MoreThanOneRootNodeException extends Exception {
    public MoreThanOneRootNodeException(String e) {
        super(e);
    }
}

public class DecisionTree {
    private Node root;
    private Hashtable<String, Node> nodeLookUpTable = new Hashtable<>();

    public static void main(String args[]) {
        DecisionTree tree = new DecisionTree();
        tree.grow("/intermediate/decision-tree-data.txt");
        tree.traverse();
    }


    public void grow(String fileName) {

        ArrayList<Edge> edgeList = growTreeFromFileStoreIntoNodeLookupTableAndReturnEdgeList(fileName);
        ArrayList<Node> nodeList = new ArrayList<>(nodeLookUpTable.values());

        // call to node.toAnswer if node.isAnswer is true (no connected nodes) and changes origins to point to new answer
        // object,
        // removes nodes that are a destination from nodeList, for we need to know the start node that is not a destination
        for (Edge edge: edgeList) {
            Node origin = edge.getOriginNode();
            Node destination = edge.getDestinationNode();
            if (destination.isAnswer()) {
                edge.setDestinationNode(destination.toAnswer());
                origin.putAnswerNodeConnection(edge);
            }
            nodeList.remove(destination);
        }
        try {
            if (nodeList.size() > 1) {
                throw new MoreThanOneRootNodeException("More than one starting node was provided in the text file.");
            }
            // There should only be one node left that is not a destination, we set it to the root node
            this.root = nodeList.get(0);
        } catch (MoreThanOneRootNodeException e) {
            e.printStackTrace();
        }
    }

    public void traverse() {
        boolean answerReached = false;
        Node currentNode = this.root;

        while (!answerReached) {
            Node questionResult = askQuestionAndReturnNode(currentNode);
            if (questionResult instanceof Answer) {
                answerReached = true;
            } else if (questionResult instanceof Node) {
                currentNode = questionResult;
                continue;
            } else if (questionResult == null) {
                continue;
            }

            leaveTree(questionResult);
        }
    }

    private void leaveTree(Node answerNode) {
        System.out.println("De boom die je net beschreven hebt is waarschijnlijk een " + answerNode.getPayloadString() + ".");
    }

    private Node askQuestionAndReturnNode(Node currentNode) {

        // print question
        System.out.println(currentNode.getPayloadString());
        // get possible answers from node
        ArrayList<String> answers = currentNode.getPossibleAnswers();

        // print answer enumerated
        for (int i = 0; i < answers.size(); i++) {
            System.out.println((i + 1) + ": " + answers.get(i));
        }

        // handle user input, return null if not valid, otherwise return node pointed to by the answer
        try {
            Scanner myScanner = new Scanner(System.in);
            String userInput = myScanner.nextLine();
            int userInputInt = Integer.parseInt(userInput);

            return currentNode.getConnectedNodeUsingAnswerKey(answers.get(userInputInt - 1));

        } catch (NumberFormatException e) {
            System.out.println("Kies alleen een nummer dat is weergeven.");
            return null;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Kies alleen een nummer dat is weergeven.");
            return null;
        }

    }

    private ArrayList<Edge> growTreeFromFileStoreIntoNodeLookupTableAndReturnEdgeList(String fileName) {

        ArrayList<Edge> edgeList = new ArrayList<>();

        try {
            String line;
            String nodePatternString = "([^,]*),\s*([^,]*)$";
            String edgePatternString = "([^,]*),\s*([^,]*),\s*([^,]*)$";

            Pattern nodePattern = Pattern.compile(nodePatternString);
            Pattern edgePattern = Pattern.compile(edgePatternString);

            URL url = getClass().getResource(fileName);
            File file = new File(url.getPath());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Read the file line by line
            while ((line = reader.readLine()) != null) {

                // Matches nodes on lines
                Matcher lineNodeMatcher = nodePattern.matcher(line);
                if (lineNodeMatcher.matches()) {

                    String name = lineNodeMatcher.group(1);
                    String payloadQuestion = lineNodeMatcher.group(2);

                    // Add node with question/answer to nodeLookupTable if it is not yet in there,
                    // otherwise it checks if the node has a question/answer string and just sets it if it wasn't set yet
                    if (!nodeLookUpTable.containsKey(name)) {
                        nodeLookUpTable.put(name, new Node());
                        nodeLookUpTable.get(name).setPayloadString(payloadQuestion);
                    } else if (nodeLookUpTable.get(name).getPayloadString() == null) {
                        nodeLookUpTable.get(name).setPayloadString(payloadQuestion);
                    }
                }

                // Matches edges on lines
                Matcher lineEdgeMatcher = edgePattern.matcher(line);
                if (lineEdgeMatcher.matches()) {

                    String originNodeName = lineEdgeMatcher.group(1);
                    String destinationNodeName = lineEdgeMatcher.group(2);
                    String payloadAnswer = lineEdgeMatcher.group(3);

                    // First, it checks if the nodes involved in the edge are in the nodeLookupTable, and adds them without setting the question/answer string yet
                    // Then, it is always possible to make an edge object using two nodes, either using those just created or those that were already in the nodeLookupTable.
                    if (!nodeLookUpTable.containsKey(originNodeName)) {
                        nodeLookUpTable.put(originNodeName, new Node());
                    }
                    if (!nodeLookUpTable.containsKey(destinationNodeName)) {
                        nodeLookUpTable.put(destinationNodeName, new Node());
                    }

                    // Creates a new edge between two node objects and an answer
                    Edge newEdge = new Edge(
                            nodeLookUpTable.get(originNodeName),
                            nodeLookUpTable.get(destinationNodeName),
                            payloadAnswer
                            );
                    // Adds a connection between nodes that can be used to traverse the tree by answering the question in nodes
                    nodeLookUpTable.get(originNodeName).putAnswerNodeConnection(newEdge);
                    // Stores the edge to use later for determining which nodes are answers, and which node is the start node.
                    edgeList.add(newEdge);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return edgeList;
    }

}
