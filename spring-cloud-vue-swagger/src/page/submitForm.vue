<template>
  <div class="content-parameter"
       v-if="swaggerCategory[countTo]&&swaggerCategory[countTo].pathInfo&&swaggerCategory[countTo].pathInfo.parameters">
    <ul>
      <li class="parameter-head">
        <input :checked="isSelectAll(copyInterfaceRequest)||selectAll" style="margin-top:10px;" type="checkbox" @click="selectAll=!selectAll"/>
        <span>参数名称</span>
        <span style="border-right: 7px solid transparent;">参数值</span>
        <span>操作</span>
      </li>
      <li class="parameter-content" v-for="(item,key) in copyInterfaceRequest">
        <input style="margin-top:10px;"  class="parameter-checkbox" type="checkbox"
               :disabled="item.required"   :checked="item.required||selectAll" />
        <input :value="item.name" class="parameter-name" type="text" />
        <div class="parameter-value">
              <textarea rows="10" v-if="parameterValue[key]!=''&&(typeof parameterValue[key])=='object'"
                        style="height:auto;width:100%;color: #858585;padding: 5px 9px;"
                        type="text">{{parameterValue[key]}}</textarea>
          <input v-else v-model="parameterValue[key]"  type="text" style="width:100%;margin-top: 8px;"/>
        </div>
        <span v-if="parameterValue[key]==''||(typeof parameterValue[key])!='object'"
              class="parameter-operating" @click="deleteInterfaceRequest(key,item)">删除</span>
      </li>
    </ul>
  </div>

</template>
<script>
  import {deepCopy,basicTypeInit} from './../util/util'
  export default {
    name:"submit-form",
    data () {
      return {selectAll:false,a:0}
    },
    props:['swaggerCategory','leftDropDownBoxContent','countTo','InterfaceRequest'],
    computed:{
      copyInterfaceRequest:function(){
          return deepCopy(this.InterfaceRequest);
      },
      parameterValue: function () {
        let resultCopy= deepCopy(this.InterfaceRequest);
        let parameterValue={};
        for(let key in resultCopy){
          /* 如果该字段没有type属性且存在子字段，子字段内有类型type属性 */
          if((!resultCopy[key].type&&resultCopy[key].properties&&resultCopy[key].properties.type=="object")||resultCopy[key].type=='array'&&resultCopy[key].properties){
            /* 包含子字段 */
            if(resultCopy[key].type=='array'&&resultCopy[key].properties){
              parameterValue[key]=[];
              parameterValue[key].push(this.$parent.iniObject(resultCopy[key].properties.properties))
            }else{
              parameterValue[key]={}
              parameterValue[key]=this.$parent.iniObject(resultCopy[key].properties.properties);
            }
          }else{
            /* 不包含子字段 */
            parameterValue[key]=basicTypeInit(resultCopy[key].type);
          }
        }
        return parameterValue;
      }
    },
    methods:{
      PromptPopUpShow: function (hint) {
          this.$layer.msg(hint, {time: 2})
      },
      deleteInterfaceRequest:function (key,item) {
        if(this.copyInterfaceRequest&&this.copyInterfaceRequest[key]){
          if(item.required){
            this.PromptPopUpShow(item.name+"为必选字段");
            return
          }
          this.$delete(this.copyInterfaceRequest,key);
          this.selectAll=!this.selectAll;
          this.selectAll=!this.selectAll;
        }
      },
      isSelectAll:function (InterfaceRequest) {
        let is=true;
        for(let key in InterfaceRequest){
          if(!InterfaceRequest[key].required){
            is=false
          }
        }
        this.s=is;
      },
    }
  }
</script>
<style>
  /* 调试：附带参数列表 */
  .content-parameter {
    margin-bottom: 45px;
    border-top: 1px solid #ddd;
  }

  .content-parameter li {
    font-weight: 700;
    border-bottom: 1px solid #ddd;
    overflow: hidden;
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

</style>
