package nl.sogyo.javaopdrachten.decisiontree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class Node {
    private String payloadString;
    private Hashtable<String, Node> connectedNodes = new Hashtable<>();

    public String getPayloadString() {
        return payloadString;
    }

    public void setPayloadString(String payloadString) {
        this.payloadString = payloadString;
    }

    public void putAnswerNodeConnection(Edge edge) {
        connectedNodes.put(edge.getAnswerString(), edge.getDestinationNode());
    }

    public Node getConnectedNodeUsingAnswerKey(String answer) {
        return connectedNodes.get(answer);
    }

    public ArrayList<String> getPossibleAnswers() {
         ArrayList<String> answers = Collections.list(connectedNodes.keys());
         return answers;
    }

    public Boolean isAnswer() {
        return connectedNodes.isEmpty();
    }

    public Answer toAnswer() {
        return new Answer(this.payloadString);
    }
}
