package com.priyanshu.Thread_Safe.In_Memory._Task_Queue.model;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TaskQueue {

    private final PriorityQueue<Task> pq;
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public TaskQueue(int capacity) {
        this.capacity = capacity;
        this.pq = new PriorityQueue<>(
                (a, b) -> b.getPriority().compareTo(a.getPriority())
        );
    }

    public boolean insert(Task task) {
        lock.lock();
        try {
            while (pq.size() == capacity) {
                notFull.await();
            }

            pq.offer(task);
            notEmpty.signal();
            return true;
        } catch (InterruptedException e) {
            System.out.println("Thread Interrupted");
            return false;
        }
        finally {
            lock.unlock();
        }
    }

    public Task remove() {
        lock.lock();
        try {
            while (pq.isEmpty()) {
                notEmpty.await();
            }

            Task t = pq.poll();
            notFull.signal();
            return t;
        }  catch (InterruptedException e) {
            System.out.println("Thread Interrupted");
            return null;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return pq.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public boolean isFull() {
        lock.lock();
        try {
            return pq.size() == capacity;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return pq.size();
        } finally {
            lock.unlock();
        }
    }

    public Task peek() {
        lock.lock();
        try {
            return pq.peek();
        } finally {
            lock.unlock();
        }
    }
}