+ 后端由于没有对json进行解析，所以@会转换成%40
+ 前端$http的获取Promis数据应该使用then
+ 序列化没有自动生成ID就去setting的Inspections中搜索UID，将序列化没有UID标识为错误，就能够进行自动生成了
+ 遇到json数组，可以直接格式化对应的字符串返回给前端就好了