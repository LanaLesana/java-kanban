package Service;
import Module.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> userHistory = new ArrayList<>();
    private final HashMap<Integer, Node> userHistoryMap = new HashMap<>();
    private final CustomLinkedList<Node> customLinkedList = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if (task== null) {
            return;
        }
        if (userHistoryMap.containsKey(task.getId())) {
            customLinkedList.removeNode(userHistoryMap.get(task.getId()));
        }
        Node nodeWithTask = new Node<>(task);
        customLinkedList.linkLast(nodeWithTask);
        userHistoryMap.put(task.getId(), nodeWithTask);


    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }

    @Override
    public void remove(int id) {
        if(userHistoryMap.containsKey(id)) {
            customLinkedList.removeNode(userHistoryMap.get(id));
            userHistoryMap.remove(id);
        }
    }

    class CustomLinkedList<T> {
        public Node<T> head;
        public Node<T> tail;
        private int size = 0;

        public void linkLast(Node<T> newNode) {
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


        public List<Task> getTasks() {
            List<Task> tasksList = new ArrayList<>();
            Node<T> current = head;

            while (current != null) {
                Task task = (Task) current.data;
                tasksList.add(task);
                current = current.next;
            }

            return tasksList;
        }

        public void removeNode(Node<T> node) {
            if (node == null) {
                return;
            }
            if (node == head) {
                head = node.next;
                node.next.prev = null;
            }

            if (node == tail) {
                tail = node.prev;
                node.prev.next = null;
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



