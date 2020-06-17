package scottxuan.cloud.core.mybatis;

import org.apache.ibatis.cache.Cache;
import scottxuan.cloud.core.redis.CacheService;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author : zhaoxuan
 */
public class MapperCache implements Cache {
    private final ReadWriteLock readWriteLock = new MapperReadWriteLock();
    private String id;

    public MapperCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        } else {
            this.id = id;
        }
    }

    private Object execute(MapperCallBack callback) {
        return callback.doWithRedis();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        execute(new MapperCallBack() {
            @Override
            public Object doWithRedis() {
                CacheService.setHash(id, o.toString(),o1);
                return null;
            }
        });
    }

    @Override
    public Object getObject(Object o) {
        return execute(new MapperCallBack() {
            @Override
            public Object doWithRedis() {
                return CacheService.getHash(id, o.toString());
            }
        });
    }

    @Override
    public Object removeObject(Object o) {
        return execute(new MapperCallBack() {
            @Override
            public Object doWithRedis() {
                return CacheService.deleteHash(id, o.toString());
            }
        });
    }

    @Override
    public void clear() {
        execute(new MapperCallBack() {
            @Override
            public Object doWithRedis() {
                CacheService.delete(id);
                return null;
            }
        });
    }

    @Override
    public int getSize() {
        return (Integer) execute(new MapperCallBack() {
            @Override
            public Object doWithRedis() {
                Map<Object, Object> result = CacheService.getHashAll(id);
                return result.size();
            }
        });
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    @Override
    public String toString() {
        return "MapperCache {" + this.id + "}";
    }
}
