package Service;
import Module.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    //private final LinkedList<Task> userHistory = new LinkedList<>();
    private final HashMap<Integer, Node> userHistory = new HashMap<>();

    @Override
    public void add(Task task) {
        if (userHistory.containsKey(task.getId())) {
            userHistory.removeNode(userHistory.get(task.getId()));
        }
        userHistory.put(task.getId(), new Node<>(task));


    }

    @Override
    public List<Task> getHistory() {
        return userHistory;
    }

    @Override
    public void remove(int id) {
        userHistory.remove(id);
    }

    class CustomLinkedList<T> {
        public Node<T> head;
        public Node<T> tail;
        private int size = 0;

        public void linkLast(T data) {
            Node<T> newNode = new Node<>(data);
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
            }
            size++;
        }


        public void getTasks() {
            List<T> tasksList = new ArrayList<>();
            Node<T> current = head;

            while (current != null) {
                tasksList.add(current.data);
                current = current.next;
            }


        }

        public void removeNode(Node<T> node) {
            if (node == null) {
                return;
            }
            if (node == head) {
                head = node.next;
            }

            if (node == tail) {
                tail = node.prev;
            }

            if (node.prev != null) {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }

            size--;

        }

    }
}



