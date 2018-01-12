<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <div class="bycdao-main">
    <div class="switch">
      <span style="cursor:pointer;" @click="switchA=0" :class="[switchA==0?'active':'']">接口说明</span>
      <span style="cursor:pointer;" @click="switchA=1" :class="[switchA==1?'active':'']">在线调试</span>
    </div>
    <div v-show="switchA==0" style="" class="bycdao-content">
      <ul class="content-list" style="">
        <li class="content-head"><b>API接口文档</b></li>
        <li><span>接口url</span><span>{{bycdaoCategory[0]?bycdaoCategory[0][0]:''}}</span></li>
        <li><span>接口名称</span><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][2].summary:""}}</span></li>
        <li><span>请求方式</span><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][1]:""}}</span></li>
        <li><span>consumes</span><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][2].consumes[0]:""}}</span>
        </li>
        <li><span>produces</span><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][2].produces[0]:""}}</span>
        </li>
        <li><span>请求参数</span><span>{{bycdaoCategory[countTo]?(bycdaoCategory[countTo][2].parameters?bycdaoCategory[countTo][2].parameters:"暂无"):""}}</span>
        </li>
        <li><span>响应Model</span><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][2].responses['200'].schema.type:""}}</span>
        </li>
        <li><span>响应参数说明</span><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][1]:""}}</span></li>
        <li><span style="float: left;margin-bottom: -1000px;padding-bottom: 1000px;">响应</span>
          <statusCode></statusCode>
        </li>
      </ul>
    </div>
    <div v-show="switchA==1" class="debugging-content">
      <div class="content-url">
        <span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][1]:""}}</span>
        <div>
          <input style="width:100%;height: 23px;line-height: 23px;" type="text"/>
        </div>
        <button type="button">发送</button>
      </div>
      <div class="content-parameter">
        <ul>
          <li class="parameter-head">
            <input type="radio" />
            <span>参数名称</span>
            <span>参数值</span>
            <span>操作</span>
          </li>
          <li>
            <input type="radio" />
            <input type="text" />
            <input type="text" />
            <span>删除</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  import statusCode from './statusCode.vue'

  export default {
    name: "app",
    data() {
      return {switchA: 0}
    },
    props: ['bycdaoCategory', 'countTo'],
    components: {statusCode},
  }
</script>
<style>
  /* 附带参数列表 */
  .content-parameter {
    border-top: 1px solid #ddd;
  }

  .content-parameter li {
    font-weight: 700;
    border-bottom: 1px solid #ddd;
  }

  .content-parameter .parameter-head {

  }

  /* 调试页面 */
  .content-url {
    overflow: hidden;
    height: 35px;
    margin-bottom: 10px;
    position: relative;
  }

  .content-url > span {
    width: 50px;
    color: #fff;
    background: #40A8FF;
    text-align: center;
    height: 100%;
    line-height: 35px;
    position: absolute;
    left: 0;
    top: 0;
  }

  .content-url > div {
    display: block;
    margin-left: 50px;
    margin-right: 88px;
    height: 35px;
  }

  .content-url > div input {
    color: #858585;
    background-color: #fff;
    border: 1px solid #d5d5d5;
    padding: 5px 4px;
    line-height: 1.2;
    font-size: 14px;
    font-family: inherit;
  }

  .content-url > button {
    position: absolute;
    right: 0;
    top: 0;
    height: 35px;
    background: #40A8FF;
    padding: 0 23px;
    border: 0;
    color: #fff;
  }

  .content-url > button:active {
    background-color: #1b6aaa !important;
    border-color: #428bca;
  }

  /* 接口详细信息列表 */
  .bycdao-content, .debugging-content {
    border-top: 1px solid #dbdbdb;
    padding: 20px 10px;
  }

  .content-list {
    text-align: left;
    margin: 0;
    padding: 0;
    border: 1px solid #DDDDDD;
  }

  .content-list > li {
    list-style: none;
    text-align: left;
    border-bottom: 1px solid #DDDDDD;
    overflow: hidden;
  }

  .content-list .content-head {
    padding: 8px;
    border-bottom-width: 2px;
    text-align: center;
  }

  .content-list > li > span {
    display: inline-block;
    width: 108px;
    padding: 8px;
  }

  .content-list > li > span:nth-child(1) {
    text-align: right;
    font-weight: 700;
    border-right: 1px solid #ddd;
  }

  /*  接口详细内容 */
  .bycdao-main {
    box-shadow: 1px 1px 5px #f3f4ef;
    border: 1px solid #F3F4EF;
    margin-left: 470px;
    margin-right: 15px;
    transition: all 0.2s;
  }

  .switch {
    margin-top: 15px;
    text-align: left;
    padding-left: 20px;
    font-size: 0;
  }

  .switch span {
    font-size: 14px;
    display: inline-block;
    padding: 10px 15px;
    border: 1px solid #EBEBEB;
    position: relative;
    top: 1px;
  }

  .switch span:nth-child(1) {
    position: relative;
    right: -1px;
  }

  .switch span.active {
    box-shadow: 0 3px #89BF05 inset;
    color: #89BF05;
    border-bottom: 1px solid transparent;
    border-bottom: 1px solid #FFFFFF;
  }
</style>
