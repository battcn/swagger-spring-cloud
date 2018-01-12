import Vue from 'vue'
import Vuex from 'vuex'
import VueResource from 'vue-resource'
Vue.use(Vuex);
Vue.use(VueResource);

/* 下拉框数据 */
const dropDown={
  state:{data:[],count:0},
  mutations:{
    switch(state,n){
      dropDown.state.count=n;
      init();
    }
  }
}
/* 下拉框下方的接口数据 */
const  bycdaoLeftContent={
  state:{data:[]}
}
/* 初始化:获取单选框数据， */
function init() {
  let a=Vue.http.get('http://localhost:8803/cloud-swagger-resources').then((responese)=>{
    dropDown.state.data=responese.body;
    return true;
  },(responese)=>{
    dropDown.state.data="请求失败:"+responese;
    return false;
  }).then(function (a) {
    if(a){
      /* dropDown.state.data[0]控制当前是第几个接口 */
      // bycdaoLeftContent.state.data=dropDown.state.data[0].swaggerResources[0]
      Vue.http.get(dropDown.state.data[dropDown.state.count].swaggerResources[0].location).then((responese)=>{
        bycdaoLeftContent.state.data=responese.body;
        return true;
      },(responese)=>{
        bycdaoLeftContent.state.data="请求失败:"+responese;
        return false;
      })
    }
  });
}
init();


export default new Vuex.Store({
  modules:{
    bycdaoLeftHead:dropDown,
    bycdaoLeftContent:bycdaoLeftContent
  }
})








