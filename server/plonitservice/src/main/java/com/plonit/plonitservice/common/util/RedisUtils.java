package com.plonit.plonitservice.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;


    public void setRedisValue(String key, Object o, Long ttl) {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        byte[] serialize = serializer.serialize(o);

        String serializeObject = Base64.getEncoder().encodeToString(serialize);

        redisTemplate.opsForValue().set(key, serializeObject, ttl, TimeUnit.SECONDS);
    }

    public void setRedisValue(String key, Object o) {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        byte[] serialize = serializer.serialize(o);

        String serializeObject = Base64.getEncoder().encodeToString(serialize);
        System.out.println("직렬화된 결과: " + serializeObject);

        redisTemplate.opsForValue().set(key, serializeObject);
    }

    public void setRedisValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setRedisHash(String key, String subKey, String value, Long ttl) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(key, subKey, value);
        stringRedisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    public void setRedisHash(String key, String subKey, String value) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(key, subKey, value);
    }

    public void setRedisSet(String key, String value) {
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        setOperations.add(key, value);
    }

    /**
     * Redis의 Sorted Set에 랭킹 추가
     * @param key
     * @param memberKey
     * @param score
     */
    public void setRedisSortedSet(String key, String memberKey, double score) {
        redisTemplate.opsForZSet().add(key, memberKey, score);
    }


    public <T> T getRedisValue(String key, Class<T> classType) throws JsonProcessingException {
        String redisValue = redisTemplate.opsForValue().get(key);

        if(redisValue == null) {
            return null;
        }

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        byte[] decode = Base64.getDecoder().decode(redisValue);

        return serializer.deserialize(decode, classType);
    }

    public String getRedisValue(String key) {
        String redisValue = redisTemplate.opsForValue().get(key);

        if(redisValue == null) {
            return null;
        }

        return redisValue;
    }

    public Map<String, String> getRedisHash(String key) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public String getRedisHash(String key, String subKey) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        return hashOperations.entries(key).get(subKey);
    }

    public Set<String> getRedisSet(String key) {
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        return setOperations.members(key);
    }

    public Boolean isRedisSetValue(String key, String value) {
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        return setOperations.isMember(key, value);
    }

    public Set<ZSetOperations.TypedTuple<String>> getSortedSetRangeWithScores(String key, long start, long end) {
        Set<ZSetOperations.TypedTuple<String>> membersWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);

        // Sorted Set에서 멤버와 Score를 가져와서 DefaultTypedTuple로 반환합니다.
        return membersWithScores;
    }

    /**
     * 총 멤버 수 가져오기
     * @param key
     * @return
     */
    public Long getSortedSetSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    public void deleteRedisHashKey(String key, String loginId) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.delete(key, loginId);
    }

    public void deleteRedisKey(String key) {
        redisTemplate.delete(key);
    }

    public void deleteRedisSet(String key, String value) {
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        setOperations.remove(key, value);
    }
}
