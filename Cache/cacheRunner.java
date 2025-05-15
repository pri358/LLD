package Cache;
import java.util.*;

/*
 Cache 
 -> multiple eviction policies -> LRU, LFU etc doubly linked list
 -> map -> actual cache
 -> 
 */

public class cacheRunner {
    
}

class Cache<K,V>
{
    Map<K,V> cacheStorage;
    IEvictionAlgorithm<K> evictionAlgorithm;
    int capacity;
}

interface IEvictionAlgorithm<K>
{
    // do something on key access
    void keyAccessed(K key);

    // evictKey 
    K evictKey();
}

class LRUEviction<K> implements IEvictionAlgorithm<K>
{
    DoubleLinkedList<K> dll;
    Map<K, Node<K>> keyToNodeMap;

    public void keyAccessed(K key)
    {
        if(keyToNodeMap.containsKey(key))
        {
            Node<K> node = keyToNodeMap.get(key);
            dll.removeNode(node);
            dll.addNodeAtTail(node);
        }
        else
        {
            Node<K> node = new Node<>(key);
            keyToNodeMap.put(key, node);
            dll.addNodeAtTail(node);
        }

    }

    public K evictKey()
    {
        // remove key at head 
        Node<K> nodeRemoved = dll.removeHead();
        return nodeRemoved.key;
    }
} 

class Node<K>
{
    Node<K> prev;
    Node<K> next;
    K key; 

    public Node(K key)
    {
        this.key = key;
        prev = null;
        next = null;
    }

    public K getKey()
    {
        return key;
    }
}

class DoubleLinkedList<K>
{
    Node<K> head; // poll
    Node<K> tail; // push
    
    public DoubleLinkedList()
    {
        head = new Node<K>(null);
        tail = new Node<K>(null);
        head.next = tail; 
        tail.prev = head;
    }

    public Node<K> removeHead()
    {
        Node<K> nodeToRemove = head.next; 
        head.next = nodeToRemove.next;
        nodeToRemove.next.prev = head;
        return nodeToRemove;
    }

    public void addNodeAtTail(Node<K> node)
    {
        tail.next = node;
        node.prev = tail;
        tail = node;
        node.next = null;
    }

    public void removeNode(Node<K> node)
    {
        if(node == null) return;
        node.prev.next = node.next; 
        node.next.prev = node.prev;
    }
    
}