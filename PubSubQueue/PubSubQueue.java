package PubSubQueue;

import java.util.*;
/*
 Requirements: 
 1.  topics 
 2. messages 
 3. Publisher 
 4. Subscriber 

 // offset optional 
 */

public class PubSubQueue {
    
}

class QueueController 
{
    HashMap<String, Topic> topics;
    HashMap<String, List<ITopicSubscriber>> topicSubscribers;

    public QueueController()
    {
        topics = new HashMap<>(); 
        topicSubscribers = new HashMap<>();
    }

    public void createTopic(String topicName)
    {
        Topic topic = new Topic(topicName);
        topics.put(topic.topicId, topic);
    }

    public void subscribe(ISubscriber subscriber, String topicId)
    {
        Topic topic = topics.get(topicId); if(topic == null) return;
        ITopicSubscriber topicSubscriber = new TopicSubscriber(topic, subscriber);
        topicSubscribers.getOrDefault(topicId, new ArrayList<>()).add(topicSubscriber);
    }

    public synchronized void publish(String topicId, Message message)
    {
        Topic topic = topics.get(topicId); 
        topic.messages.add(message);
        for(ITopicSubscriber sub: topicSubscribers.get(topicId))
        {
            sub.notifySub(message);
        }
    }

}

class Topic 
{
    String topicId;
    String topicName;
    Queue<Message> messages;

    public Topic(String topicName)
    {
        this.topicName = topicName;
        topicId = UUID.randomUUID().toString();
        messages = new LinkedList<>();
    }
}

interface ITopicSubscriber
{
    public void notifySub(Message message);
}

class TopicSubscriber implements ITopicSubscriber
{
    Topic topic; 
    ISubscriber subscriber; 

    public TopicSubscriber(Topic topic, ISubscriber subscriber)
    {
        this.topic = topic;
        this.subscriber = subscriber;
    }

    public synchronized void notifySub(Message message)
    {
        // logic to read maybe 
        subscriber.onMessage(message);
    }

}

interface ISubscriber 
{
    public String getId();
    public void onMessage(Message message);
}

class SimpleSubscriber implements ISubscriber
{
    final String id;
    public SimpleSubscriber()
    {
        id = UUID.randomUUID().toString();
    }

    public String getId()
    {
        return this.id;
    }

    public synchronized void onMessage(Message message)
    {
        System.out.println(message);
    }
}

interface IPublisher
{
    public String getId();
    void publish(String topicId, Message message);
}

class SimplePublisher implements IPublisher
{
    final String id;
    QueueController queueController;
    
    public SimplePublisher()
    {
        id = UUID.randomUUID().toString();
    }

    public String getId()
    {
        return this.id;
    }

    public void publish(String topicId, Message message)
    {
        queueController.publish(topicId, message); 
    }

}


class Message
{
    String content; 
    String timestamp;
    //metadata
}