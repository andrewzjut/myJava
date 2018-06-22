local resp = 0;
local result = redis.call('set', KEYS[1], ARGV[1],  ARGV[2],  ARGV[3],  100000);
if result then
    resp = 1
    return resp
else
    if redis.call('get', KEYS[1]) == ARGV[1] then
        resp = 1
    else resp = 0
    end
end
return resp
