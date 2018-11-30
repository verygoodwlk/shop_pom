--获得需要秒杀的商品id
local id = KEYS[1]
local number = tonumber(ARGV[1])
local orders = ARGV[2]

--获得秒杀的商品库存
local save = tonumber(redis.call('get', 'kill'..id))

--判断库存
if save == nil or save <= 0 then
  --商品不存在，或者库存不足，则返回2表示秒杀失败
  return 2
end

--库存足够
save = save - number
redis.call('set', 'kill'..id, save)

--添加订单
redis.call('rpush', 'orders'..id, orders)

--秒杀成功 返回结果
if save == 0 then
    return 0
else
    return 1
end
