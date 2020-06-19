package com.scottxuan.core.mybatis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author : zhaoxuan
 */
public class MapperReadWriteLock implements ReadWriteLock {
    private Lock lock = new MapperReadWriteLock.DummyLock();

    MapperReadWriteLock() {
    }

    @Override
    public Lock readLock() {
        return this.lock;
    }

    @Override
    public Lock writeLock() {
        return this.lock;
    }

    static class DummyLock implements Lock {
        DummyLock() {
        }

        @Override
        public void lock() {
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
        }

        @Override
        public boolean tryLock() {
            return true;
        }

        @Override
        public boolean tryLock(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
            return true;
        }

        @Override
        public void unlock() {
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }
}
