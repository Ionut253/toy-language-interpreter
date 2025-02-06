package Model.ADTs;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTable<K,V> implements  MyILockTable<K, V>{

    ConcurrentHashMap<K, V> lockTable;
    int freeLocation;
    private Lock lock;

    public LockTable(){
        lockTable = new ConcurrentHashMap<>();
        freeLocation = 0;
        lock = new ReentrantLock();
    }

    public int getFreeLocation() {
        lock.lock();
        try {
            return ++freeLocation;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void add(K key, V val) {
        lock.lock();
        try {
            lockTable.put(key, val);
        }
        finally {
                lock.unlock();
            }
    }

    @Override
    public V lookup(K key) {
        lock.lock();
        try {
            return lockTable.get(key);
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void remove(K key) {
        lockTable.remove(key);
    }

    @Override
    public boolean isDefined(K key) {
        lock.lock();
        try {
            return lockTable.containsKey(key);
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void update(K key, V value) {
        lock.lock();
        try {
            lockTable.put(key, value);
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public ConcurrentHashMap<K, V> get(K key) {
        lock.lock();
        try {
            return lockTable;
        }
        finally {
            lock.unlock();
        }

    }

    public ConcurrentHashMap<K, V> getContent() {
        lock.lock();
        try {
            return lockTable;
        }
        finally {
            lock.unlock();
        }
    }
}
