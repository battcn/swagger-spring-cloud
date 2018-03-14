/* 对象拷贝 */
const deepCopy = function (source) {
  let result;
  if (typeof source === 'object') {
    if (source.constructor == Array)  {
      return treatArray(source);
    }
    return treatObj(source);
  } else {
    return treatBasic(source);
  }

  function treatObj(source) { //处理对象类型
    result = {};
    for (let key in source) {
      result[key] = deepCopy(source[key]);
    }
    // console.log(result);
    return result;
  }

  function treatArray(source) { //处理list数组类型
    result = [];
    for (let key in source) {
      result.push(deepCopy(source[key]));
    }
    // console.log(result)
    return result;
  }

  function treatBasic(source) { //处理基本类型
    result = source;
    return result;
  }
  /*for (let key in source) {
      if(typeof source === 'object'&&source[key]&&source[key].constructor&&source[key].constructor==Array){
        result[key]=[];
        result[key].push(deepCopy(source[key]));
        continue;
      }
    result[key] = (typeof source[key] === 'object') ? deepCopy(source[key]):source[key];
  }
  return result;*/
}
/* 传入参数类型名字，返回该类型初始化的值 */
const basicTypeInit = function (type) {
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
export {
  deepCopy, basicTypeInit
}
