package beginner.lists;

import java.util.List;

public class Lists {
    private List<Integer> theList;

    public static void main(String[] args) {
        Lists ListsObj = new Lists();

        Integer testing = 1;
        ListsObj.theList.add(testing);
        ListsObj.printList(ListsObj.theList);

    }
    public void printList(List localList) {
        for (Object element: localList) {
            System.out.print(element);
        }
    }
}
