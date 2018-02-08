/* 对象拷贝 */
const deepCopy=function(source){
  var result = {};
  for (var key in source) {
    result[key] = (typeof source[key] === 'object') ? deepCopy(source[key]) : source[key];
  }
  return result;
}
/* 传入参数类型名字，返回该类型初始化的值 */
const basicTypeInit=function (type) {
  if (type == 'integer' || type == 'number') {
    return 0;
  }
  if (type == 'boolean') {
    return 'false'
  }
  if (type == 'string') {
    return ""
  }
  if (type == 'array') {
    return []
  }
}
export{
  deepCopy,basicTypeInit
}
