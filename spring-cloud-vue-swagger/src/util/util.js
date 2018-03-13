/* 对象拷贝 */
const deepCopy=function(source){
  let result={};
  //typeof source === 'object'?(Array==source.constructor ?result = []:result = {}):result = {};
  for (let key in source) {
    /*  // typeof source[key] === 'object'? result = {}:((typeof source[key] === 'array')?result = []:result = {});
      if(typeof source === 'object'&&source[key]&&source[key]['constructor']&&source[key].constructor == Array){
        result[key]=[];
        result[key].push(deepCopy(source[key]));
        continue;
      }*/
    result[key] = (typeof source[key] === 'object') ? deepCopy(source[key]):source[key];
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
