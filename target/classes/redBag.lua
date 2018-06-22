-- keys[4] userId
-- keys[3] 存储用枪红包记录
-- keys[1] 红包池
if redis.call('hexists',KEYS[3],KEYS[4]) ~= 0 then
    return nil
else
    local hongbao = redis.call('rpop',KEYS[1]);
    if hongbao then
        local x = cjson.decode(hongbao);
        x['userId'] = KEYS[4];
        local re = cjson.encode(x);
        redis.call('hset',KEYS[3],KEYS[4],'1');
        redis.call('lpush',KEYS[2],re);
        return re;
    end
end
return nil;