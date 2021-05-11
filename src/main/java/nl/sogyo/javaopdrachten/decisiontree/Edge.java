package nl.sogyo.javaopdrachten.decisiontree;

public class Edge {
    private Node originNode, destinationNode;
    private String answerString;

    public Edge(Node originNode, Node destinationNode, String answerString) {
        this.originNode = originNode;
        this.destinationNode = destinationNode;
        this.answerString = answerString;
    }

    public Node getOriginNode() {
        return originNode;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;
    }

    public String getAnswerString() {
        return answerString;
    }

}
