package beginner.lists;

import java.util.ArrayList;
import java.util.List;

public class Lists {
    private List<Integer> theList = new ArrayList<Integer>();

    public interface MyCompare {
        public boolean compare(int i1, int i2);
    }

    public interface MyFilter {
        public boolean filter(int i1);
    }

    public static void main(String[] args) {
        // Make main object
        Lists ListsObj = new Lists();

        // Generate main list of 10 random integers between 0 and 100
        for (int i = 0; i < 10; i++) {
            int randNo = (int) (Math.random() * 100);
            ListsObj.theList.add(randNo);
        }

        // Print main list
        ListsObj.printList("List of 10 random integers between 0 and 100: ", ListsObj.theList);

        // Get highest element using the lambda function interface to pass a lambda function as element
        int Highest = ListsObj.getElement(ListsObj.theList, (int a1, int a2) -> {
            return a1 > a2;
        }
        );
        System.out.println("Highest element = " + Highest);

        // Get lowest with lambda
        int Lowest = ListsObj.getElement(ListsObj.theList, (int a1, int a2) -> {
                    return a1 < a2;
                }
        );
        // System.out.println("Lowest element = " + Lowest);

        // Copy main list, and remove lowest to find second lowest
        //
        int LowestIndex = ListsObj.theList.indexOf(Lowest);
        List<Integer> copyList = new ArrayList<Integer>(ListsObj.theList);
        copyList.remove(LowestIndex);
        // ListsObj.printList("copy with lowest removed: ", copyList);
        // ListsObj.printList("original: ", ListsObj.theList);
        int Lowest2 = ListsObj.getElement(copyList, (int a1, int a2) -> {
                    return a1 < a2;
                }
        );
        System.out.println("Lowest two added up = " + (Lowest2 + Lowest));


        // Filter for all even elements
        List<Integer> evenElements = ListsObj.filterTheList((int a1) -> {
            return (a1 % 2 == 0);
        });
        ListsObj.printList("All even elements: ", evenElements);

        // Set up to do multiple filters, and copy list so we can remove filtered elements
        int[] divs = {2, 3, 5};
        List<Integer> copyBefore = new ArrayList<Integer>(ListsObj.theList);
        List<List> generatedLists= new ArrayList<List>();

        // Loop that generates four lists filtering using divs and removing from copy, stores them in generatedLists
        for (int factor: divs) {
            List<Integer> filteredList = ListsObj.filterTheList((int a1) -> {
                return (a1 % factor == 0);
            });
            ListsObj.printList("Can be divided by " + factor + ": ", filteredList);
            generatedLists.add(filteredList);
            for (int ele: filteredList) {
                int index = copyBefore.indexOf(ele);
                if (index != -1)
                    copyBefore.remove(index);
            }
            generatedLists.add(copyBefore);
        }
        ListsObj.printList("Remaining numbers: ", copyBefore);

        // Implements the bubblesort algorithm and converts list to array and back
        List<Integer> sortedList = ListsObj.sortTheList();
        ListsObj.printList("List sorted with bubble sort: ", sortedList);

    }

    public List<Integer> filterTheList(MyFilter filter) {
        // Make new empty list
        List<Integer> filteredList = new ArrayList<Integer>();
        // loop over instance variable list and store in new list if filter condition is met
        for (int element: theList) {
            if (filter.filter(element)) {
                filteredList.add(element);
            }
        }

        return filteredList;
    }


    public void printList(String prompt, List<Integer> localList) {
        System.out.print(prompt + " [");

        for (int i = 0; i < localList.size(); i++) {
            int element = localList.get(i);
            if (i != localList.size() - 1) {
                System.out.print(element + ", ");
            } else {
                System.out.print(element);
            }
        }

        System.out.println("]");
    }


    public int getElement(List<Integer> list, MyCompare filter) {
        // Set initial filtered element to nonsense value
        int filteredEle = -1;
        for (Integer element: list) {
            // if initial assign it
            if (filteredEle == -1) {
                filteredEle = element;
            }
            // if comparison is true assign new element
            if (filter.compare(element, filteredEle)) {
                filteredEle = element;
            }
        }
        return filteredEle;
    }


    public List<Integer> sortTheList() {
        // converting the list instance object to array
        List<Integer> sortedCopy = new ArrayList<Integer>(theList);
        int[] sorted = sortedCopy.stream().mapToInt(i->i).toArray();

        // bubble sort algorithm with array
        int tmp;
        for (int j = 0; j < sorted.length; j++) {
            for (int i = 1; i < sorted.length - j; i++) {
                if (sorted[i - 1] > sorted[i]) {
                    tmp = sorted[i];
                    sorted[i] = sorted[i-1];
                    sorted[i - 1] = tmp;
                }
            }
        }

        // Convert array back to list and return it
        List<Integer> sortedList = new ArrayList<Integer>();
        for (int i: sorted) {
            sortedList.add(i);
        }
        return sortedList;
    }

}
