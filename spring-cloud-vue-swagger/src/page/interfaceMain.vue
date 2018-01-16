<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <div class="bycdao-main">
    <div class="switch">
      <span style="cursor:pointer;" @click="switchA=0" :class="[switchA==0?'active':'']">接口说明</span>
      <span style="cursor:pointer;"  @click="switchA=1" :class="[switchA==1?'active':'']">在线调试</span>
    </div>
    <div v-show="switchA==0" style="" class="bycdao-content">
      <ul class="content-list" style="">
        <li><span>接口url</span>
          <div><span>{{bycdaoCategory[0]?bycdaoCategory[0][0]:''}}</span></div>
        </li>
        <li><span>接口名称</span>
          <div><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][2].summary:""}}</span></div>
        </li>
        <li><span>请求方式</span>
          <div><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][1]:""}}</span></div>
        </li>
        <li><span>consumes</span>
          <div><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][2].consumes[0]:""}}</span></div>
        </li>
        <li><span>produces</span>
          <div><span>{{bycdaoCategory[countTo]?bycdaoCategory[countTo][2].produces[0]:""}}</span></div>
        </li>
        <li><span>请求参数</span>
          <div>
            <div class="request-table" v-if="bycdaoCategory[countTo]&&bycdaoCategory[countTo][2].parameters">
              <ul>
                <li class="table-tr table-head">
                  <span class="table-td">参数名称</span>
                  <span class="table-td">说明</span>
                  <span class="table-td">类型</span>
                  <span class="table-td">条件</span>
                  <span class="table-td">in</span>
                  <span class="table-td">是否必须</span>
                </li>
                <li class="table-tr" v-for="(item,index) in bycdaoCategory[countTo][2].parameters">
                  <span class="table-td">{{item.name}}</span>
                  <span class="table-td">{{item.description?item.description:"无"}}</span>
                  <span class="table-td">{{item.type}}</span>
                  <span class="table-td">无</span>
                  <span class="table-td">{{item.in}}</span>
                  <span class="table-td">{{item.required}}</span>
                </li>
              </ul>
            </div>
            <span v-else>暂无</span>
          </div>
        </li>
        <li><span>响应Model</span>
          <div><span>{{jsonData}}</span></div>
        </li>
        <li><span >响应参数说明</span>
          <div class="ResponseParameter">
            <span v-show="(typeof InterfaceResponse) != 'object'">{{InterfaceResponse}}</span>
            <ul v-show="(typeof InterfaceResponse) == 'object'">
              <li class="head"><span>参数名称</span><span>类型</span><span>说明</span></li>
              <li v-for="(item,index) in InterfaceResponse">
                <span>{{index}}</span>
                <span>{{item.type}}</span>
                <span>{{item.description?item.description:"无"}}</span>
              </li>
            </ul>
          </div>
        </li>
      </ul>
    </div>
    <div v-show="switchA==1" class="debugging-content">
      <div class="content-url">
        <span :style="{backgroundColor:bg[bycdaoCategory[countTo][1].toUpperCase()]}">{{bycdaoCategory[countTo]?bycdaoCategory[countTo][1].toUpperCase():""}}</span>
        <div>
          <input v-bind:value="(bycdaoCategory[0]&&bycdaoCategory[0][0])?bycdaoCategory[0][0]:''"
                 style="width:100%;height: 23px;line-height: 23px;" type="text"/>
        </div>
        <button type="button" @click="getForm">发送</button>
      </div>
      <div class="content-parameter" v-if="bycdaoCategory[countTo]&&bycdaoCategory[countTo][2].parameters">
        <ul>
          <li class="parameter-head">
            <input style="margin-top:10px;" type="checkbox" @click="selectAll=!selectAll"/>
            <span>参数名称</span>
            <span style="border-right: 7px solid transparent;">参数值</span>
            <span>操作</span>
          </li>
          <li   class="parameter-content" v-for="(item,index) in bycdaoCategory[countTo][2].parameters">
            <input style="margin-top:10px;"    class="parameter-checkbox" type="checkbox"
                   :checked="item.required||selectAll" />
            <input :value="item.name" class="parameter-name" type="text"/>
            <input class="parameter-value" type="text"/>
            <span class="parameter-operating">删除</span>
          </li>
        </ul>
      </div>
      <div class="debugging-result" v-show="resultShow">
        <span style="cursor:pointer;" @click="debugging='content'"
              :class="[debugging=='content'?'active':'']">响应内容</span>
        <span style="cursor:pointer;" @click="debugging='cookies'"
              :class="[debugging=='cookies'?'active':'']">Cookies</span>
        <span style="cursor:pointer;" @click="debugging='header'"
              :class="[debugging=='header'?'active':'']">Header</span>
        <span style="cursor:pointer;" @click="debugging='curl'" :class="[debugging=='curl'?'active':'']">curl方式</span>
        <div class="result-content">
          <div v-show="debugging=='content'">
            <ul v-if="(typeof debugResponse.bodyText)=='object'">
              <li v-for="item in debugResponse.bodyText">
                <span>{{item}}</span>
              </li>
            </ul>
            <li v-else>
              <span>{{debugResponse.bodyText}}</span>
            </li>
          </div>
          <div v-show="debugging=='cookies'">
            <span>暂无</span>
          </div>
          <div class="debugging-header" v-show="debugging=='header'">
            <ul style="border: 1px solid #ddd;">
              <li class="head"><span>请求头</span><span>value</span></li>
              <li><span>date</span><span></span></li>
              <li><span>transfer-encoding</span><span></span></li>
              <li><span>x-application-context</span><span></span></li>
              <li><span>content-type</span><span>{{debugResponse&&debugResponse.headers&&debugResponse.headers['map']&&debugResponse.headers['map']['content-type']&&debugResponse.headers['map']['content-type'][0]}}</span></li>
              <li><span>response-code</span><span>{{debugResponse&&debugResponse.status}}</span></li>
            </ul>
          </div>
          <div class="debugging-curl" v-show="debugging=='curl'">
            <div>
              {{curlMode}}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  import {mapMutations} from 'vuex'
  export default {
    name: "app",
    data() {
      return {switchA: 0, resultShow:false,debugging: 'content', selectAll:false ,curlMode: "",jsonData:""}
    },
    computed: {
      InterfaceResponse:function(){
        let resp=this.bycdaoCategory[this.countTo]&&this.bycdaoCategory[this.countTo][2].responses;
        var respBasis = false;
        var respState;
        for (let  key in resp) {
          if (parseInt(key) >= 200 && parseInt(key) <= 299) {
            respBasis = true;
            respState = key;
            break;
          }
        }
        if (respBasis) {
          let ok = resp[respState];
          if (ok.hasOwnProperty("schema")) {
            let schema = ok["schema"];
            let ref = (schema["type"] && schema["type"] === "array" && schema["items"]) ? schema["items"].$ref : schema["$ref"];
            let regex = new RegExp("#/definitions/(.*)$", "ig");
            if (regex.test(ref)) {
              let refType = RegExp.$1;
              let flag = false;
              let definitionsArray = this.bycdaoCategory[this.countTo]&&this.bycdaoCategory[this.countTo][3].definitions;
              let deftion = null;
              let definition = null;
              for(let i in definitionsArray){
                if(i=== refType){
                  flag = true;
                  deftion =definitionsArray[i];
                  break
                }
              }
              for (var key in deftion.properties) {
                if (deftion.properties[key].type == "boolean") {
                  deftion.properties[key] = true;
                  continue;
                }
                if (deftion.properties[key].type == "integer") {
                  deftion.properties[key] = 0 ;
                  continue;
                }
                if ( deftion.properties[key].type == "string") {
                  deftion.properties[key] = "String";
                  continue;
                }
                if ( deftion.properties[key].type == "array") {
                  deftion.properties[key] = "String";
                  continue;
                }
              }
              if (flag) {
                this.jsonData=deftion.properties;
              } else {
                this.jsonData=refType;
              }
              return deftion&&deftion.properties;
            }else {
              //未发现ref属性
              if (schema.hasOwnProperty("type")) {
                this.jsonData=schema["type"]
                return schema["type"];
              }
              return "无";
            }
          }
        }
      },
      debugResponse() {
        return this.$store.state.debugRequest.debugResponse;
      }
    },
    watch:{
      countTo:function () {
//        this.curlMode="";
        this.switchA=0;
//        this.debugResponse&&this.debugResponse.bodyText?this.debugResponse.bodyText="":"";
//        this.debugResponse&&this.debugResponse.headers&&
//        this.debugResponse.headers['map']&&this.debugResponse.headers['map']['content-type']&&
//        this.debugResponse.headers['map']['content-type'][0]?
//          this.debugResponse.headers['map']['content-type'][0]="":"";
        this.resultShow=false;
      }
    },
    methods: {
      getForm: function () {
        var _this = this;
        var result = [];
        var parameterContent = document.getElementsByClassName("parameter-content");
        for (var i = 0, n = parameterContent.length; i < n; i++) {
          var option = parameterContent[i].children[0];
          if(this.bycdaoCategory[this.countTo][2].parameters[i].required==true&&!parameterContent[i].children[0].checked){
                 alert(parameterContent[i].children[1].value+"");
                 return false;
          }
          if (option.checked) {
            if(parameterContent[i].children[2].value.trim().length==0){
              alert(parameterContent[i].children[1].value+"不能为空");
              return false;
            }
            var obj = [];
            obj.push(parameterContent[i].children[1].value);
            obj.push(parameterContent[i].children[2].value)
            obj.push(_this.bycdaoCategory[_this.countTo][2].parameters[i])
            result.push(obj);
          }
        }
        _this.stitchUrl(result);
      },
      stitchUrl: function (result) {
        let _this = this;
        let url = (_this.bycdaoCategory&&_this.bycdaoCategory[0] && _this.bycdaoCategory[0][0]) ? _this.bycdaoCategory[0][0] : '',
          params = {},
          headerParams = "",
          reqdata = "",
          bodyparams = "";
        if (typeof (_this.bycdaoCategory[0][3].basePath) !== "undefined" && _this.bycdaoCategory[0][3].basePath !== "") {
          if (_this.bycdaoCategory[0][3].basePath !== "/") {
            url = _this.bycdaoCategory[0][3].basePath + url;
          }
        }
        for (let i = 0, n = result.length; i < n; i++) {
          if (result[i][2]["in"] === "path") {
            url = url.replace("{" + result[i][0] + "}", result[i][1]);
          } else {
            if (result[i][2]["in"] === "body") {
              bodyparams += result[i][1];
            } else {
              if (result[i][2]["in"] === "header") {
                headerParams[result[i][0]] = result[i][1];
              } else {
                result[i][1] ? params[result[i][0]] = result[i][1] : '';
              }
            }
          }
        }
        reqdata = params;
        let jsonReqdata = JSON.stringify(reqdata)
        this.$store.commit('send', {
          url: "http://" + _this.bycdaoCategory[0][3].host + url,
          headerParams: headerParams,
          type: _this.bycdaoCategory[this.countTo][1],
          data: reqdata
        });
        // this.debugResponse=this.$store.state.debugRequest.data;
        /* 冰洁curl口令 */
        setTimeout(() => {
          _this.StitchingCurl(headerParams, jsonReqdata);
        }, 1000);
      },
      StitchingCurl: function (headerParams, reqdata) {
        let _this = this;
        let headerss = "";
        let contentUrl = "\'" + _this.debugResponse.url + "\'";
        let curlAccept = " --header \'Accept:  " + _this.debugResponse.headers['map']['content-type'][0] + "\' ";
        for (let key in headerParams) {
          headerss += (key + ": " + headerParams[key]);
        }
        /*  生成curl命令组成部分 */
        /* 头部数据 */
        headerss != "" ? headerss = " --header \'" + headerss + "\' " : "";
        let contentType = " --header \'Content-Type:  " + _this.debugResponse.headers['map']['content-type'][0] + "\' "
        if (_this.bycdaoCategory[this.countTo][1].toLowerCase() == 'get') {
          let curltable = ("curl -X " + _this.bycdaoCategory[this.countTo][1] +
            " --header \'Accept:  " + _this.debugResponse.headers['map']['content-type'][0] + "\' " +
            headerss + contentUrl);
          _this.curlMode = curltable;
          console.log(curltable)
        } else {
          /* d data 非头部附带数据,只用于非get类型请求 */
          let curlData = " -d \'  ";
          console.log()
          for (let i in JSON.parse(reqdata)) {
            curlData += i + "=" + JSON.parse(reqdata)[i] + "&";
          }
          curlData = curlData.slice(0, curlData.length - 1);
          curlData += "\' ";
          let curltable = ("curl -X " + _this.bycdaoCategory[this.countTo][1] + contentType + curlAccept + headerss + (reqdata=='{}'?"":curlData) + contentUrl);
          _this.curlMode = curltable;
        }
        this.resultShow=true;/* 显示结果 */
      },
      ...mapMutations(['send']),
    },
    props: ['bycdaoCategory', 'countTo','bg'],
  }
</script>
<style>
  /* 响应参数说明部分 */
  .ResponseParameter .head{
    font-size: 16px;
    font-weight: 700;
    background-color: #F8F8F8;
  }
  .ResponseParameter >ul{
      overflow: hidden;border:1px solid #ddd;
  }
  .ResponseParameter >ul li{
    overflow: hidden;
    border-bottom:1px solid #ddd;
  }
  .ResponseParameter >ul li:last-child{
    border-bottom:0;
  }
  .ResponseParameter >ul li> span{
    width: 30%;
    float: left;    padding: 8px 4px;border-right:1px solid #ddd;
  }
  .ResponseParameter >ul li> span:last-child{
    border-right:0;
  }
  /* 调试结果区域样式 */
  .debugging-result {
    font-size: 0;
  }

  .debugging-result > span {
    font-size: 14px;
    display: inline-block;
    padding: 10px 15px;
    border: 1px solid #EBEBEB;
    position: relative;
    border-right: 0;
    bottom: -1px;
  }

  .debugging-result > span:last-of-type {
    border-right: 1px solid #EBEBEB;
  }

  .debugging-result > span.active {
    box-shadow: 0 3px #89BF05 inset;
    color: #89BF05;
    border-bottom: 1px solid transparent;
    border-bottom: 1px solid #FFFFFF;
  }

  .debugging-curl > div {
    color: #393939;
    font-size: 13px;
    white-space: nowrap;
    overflow-x: auto;
    padding: 15px;
  }

  .result-content {
    border-top: 1px solid #EBEBEB;
    padding-top: 40px;
  }

  .result-content > div {
    border: 1px solid #ddd;
    min-height: 210px;
    font-size: 16px;
    text-align: left;
    padding: 40px 15px 15px;
  }

  .debugging-header > ul {
    border: 1px solid #ddd;
  }

  .debugging-header li {
    border-bottom: 1px solid #ddd;
  }

  .debugging-header li > span {
    padding: 10px 15px 10px 5px;
    display: inline-block;
  }

  .debugging-header li > span:first-child {
    font-weight: 700;
    width: 38%;
    border-right: 1px solid #ddd;
  }

  .debugging-header li > span:last-child {
    width: 45%;
  }

  .debugging-header .head {
    font-weight: 700;
  }

  /* 请求参数表格 */
  .table-tr {
    border-bottom: 1px solid #ddd;
    overflow: hidden;
    word-wrap: break-word;
  }

  .table-head {
    font-size: 16px;
    font-weight: 700;
    background-color: #F8F8F8;
  }

  .table-head .table-td {
    padding: 8px 4px;
  }

  .table-td {
    border-right: 1px solid #ddd;
    width: 15%;
    float: left;
    padding: 8px 4px;
    /*padding-bottom: 1000px;*/
    /*margin-bottom: -1000px;*/
    text-align: left;
  }

  .table-td:last-child {
    border-right: 0;
  }

  /* 调试：附带参数列表 */
  .content-parameter {
    margin-bottom: 45px;
    border-top: 1px solid #ddd;
  }

  .content-parameter li {
    font-weight: 700;
    border-bottom: 1px solid #ddd;
    height: 40px;
  }

  .content-parameter .parameter-head {
    overflow: hidden;
  }

  /* 首行单独样式 */
  .parameter-head input {
    width: 5%;
    float: left;
    height: 20px;
    margin-top: 10px;
  }

  .parameter-head span {
    width: 20%;
    float: left;
  }

  .parameter-head span:nth-child(3) {
    width: 48%;
    float: left;
    margin-left: 2%;
    margin-right: 2%;
  }

  /* 非首行样式 */
  .parameter-checkbox {
    width: 5%;
    float: left;
    height: 20px;
    margin-top: 10px;
  }

  /* 表格中的文字样式 */
  .content-parameter li > span {
    width: 20%;
    text-align: left;
    line-height: 40px;
    float: left;
  }

  .content-parameter li > input {
    margin-top: 8px;
  }

  .content-parameter .parameter-name {
    width: 20%;
    float: left;
  }

  .content-parameter li .parameter-value {
    width: 48%;
    float: left;
    margin-left: 2%;
    margin-right: 2%;
  }

  .content-parameter li .parameter-operating {
    font-size: 14px;
    cursor: pointer;
    color: #40A8FF;
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
    padding: 15px;
    border: 1px solid #DDDDDD;
  }

  .content-list > li {
    list-style: none;
    text-align: left;
    border-bottom: 1px solid #DDDDDD;
    overflow: hidden;
  }



  .content-list > li > span {
    display: inline-block;
    width: 108px;
    padding: 8px;
  }

  .content-list > li > div {
    margin-left: 125px;
    padding: 8px;
  }

  .content-list > li > div > span {
    display: inline-block;
    padding: 8px;
  }

  .content-list > li > div .request-table {
    padding: 8px;
  }
  .content-list > li > div .request-table >ul{
    border:1px solid #ddd;
    border-bottom:0;
  }

  .content-list > li > span:nth-child(1) {
    font-weight: 700;
    border-right: 1px solid #ddd;
    margin-bottom: -1000px;
    padding-bottom: 1000px;
    min-height: 26px;
    vertical-align: top;
    float: left;
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
