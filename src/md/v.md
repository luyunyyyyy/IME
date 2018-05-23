# 算法伪代码 维特比

##变量表
- V:List<HashMap<String, PQ<Double, List<String>>>> 
v是一个长度为2的数组, 是用来保存当前状态和上一状态的信息, 类似一个循环队列.
v中每个元素保存的是
- t: 处理拼音序列的位置
- idx v的当前id
- cur_obs 当前位置的拼音
- pylistlen 长度
- cur_cand_states 当前位置候选拼音对应的汉字
- score 当前计算的分数 概率的log
- Pi_state state 对应的初始概率
- _cur_obs 当前路径 对应汉字
- prev_states 表示之前的候选汉字
## 代码

### 对初始状态进行初始化
```
for(state : cur_cand_states)
    score = Pi_state(state) + emit_a_b(state, cur_obs) 初始概率及转移概率的乘积
    _path = [state]
    v[0].put(state, PQ())
    v[0][state].put(score, _path)
    
for t in range(1, pylistlen)
    cur_obs = pylist[t] 设置当前的操作拼音
    idx = t % 2 idx 向后移动一位
    v[idx] = null 清空上上个v的状态
    prev_states = cur_cand_states
    cur_cand_states = py2ch[cur_obs]  cur_obs 的对应汉字
    
    for state in cur_cand_states 当前对应汉字集合每个元素
        for prev in prev_states  前一个状态的每一个汉字
            for cand in V[(idx + 1) % 2][prev] 表示遍历上次运算得到集合的得分和路径
            类似于一个两个状态之间的全连通路
            score = trans_a_b(prev, state) + emit_a_b(state, cur_obs)
            是计算一个转移概率和发射概率 类似于图中一条路对应的值
            newscore = score + cand.score 得分相加 对应概率连乘之后取对数
            _p = cand.path + [state] 路径追加上
            V[idx][state].put(new_tao, _p) 保存到当前位置对应的汉字的一个路径得分

resule = PQ
for last_state in V[idx] idx表示当前处理过的最后一个状态 遍历这个状态的所有值
    for item in V[idx][last_state] 遍历这个节点
        results.put(item.score, item.path) 每个节点保存了之前一个状态到当前节点的top k
        
def Pi_state(Pi, state):
    if state in Pi:
        return max(Pi[state], MIN_PROB)
    else:
        return MIN_PROB
def trans_a_b(trans, a, b):
    if a in trans:
        if b in trans[a]:
            return max(trans[a][b], MIN_PROB)
    return MIN_PROB
def emit_a_b(emit, a, b):
    if a in emit:
        if b in emit[a]:
            return max(emit[a][b], MIN_PROB)
    return MIN_PROB
```
    
