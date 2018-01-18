<template xmlns:v-bind="http://www.w3.org/1999/xhtml" xmlns:v-on="" xmlns: xmlns:>
  <div style="padding-top:5px;">
    <div class="bycdao-left" style="height: 100%;overflow-y: auto;overflow-x: hidden;">
      <ul class="nav-list">
        <select class="form-control" v-model.lazy="selected">
          <option v-for="(item,index) in bycdaoLeftHead" :value="index">
            {{index===0?"default:":""}}{{item.swaggerResources[0].location}}
          </option>
        </select>
        <li v-for="(item,index) in leftDropDownBoxContent.tags" @click="count=index"
            v-bind:class="[count==index ? 'active' : '']">
          <i class="fa fa-file-text"></i>
          <span class="navList-name">{{item.name}}</span>
          <span class="navList-description">{{item.description}}</span>
          <span class="navList-number">{{Object.keys(leftDropDownBoxContent.paths[Object.keys(leftDropDownBoxContent.paths)[index]]).length}}</span>
        </li>
      </ul>
    </div>
    <div class="bycdao-category" style="height: 100%;overflow-y: auto;overflow-x: hidden;">
      <ul style="margin: 0;padding: 0;">
        <li class="categoryLi" v-for="(item,index) in bycdaoCategory" @click="countTo=index"
            :style="{backgroundColor:bg[item[1].toUpperCase()]}">
          <span class="categoryLi-type">{{item[1]?item[1].toUpperCase():""}}</span>
          <span class="categoryLi-name">{{item[2]&&item[2].summary?item[2].summary:""}}</span>
        </li>
      </ul>
    </div>
    <interfaceMain v-on:PromptPopUpShow="PromptPopUpShow" v-bind:bg="bg" v-bind:bycdaoCategory="bycdaoCategory" v-bind:countTo="countTo"></interfaceMain>
    <transition name="fade">
      <div v-show="control" class="popUps">
        <div>
          <h2 style="color: #c94e50;"><strong>{{hint}}</strong></h2>
          <div>
            <button @click="PromptPopUpHide"
                    style="padding: 1em 2em;outline: none;font-weight: 600;border: none;color: #fff;background: #c94e50;">
              close
            </button>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>
<script type="text/ecmascript-6">
  import {mapMutations} from 'vuex'
  import interfaceMain from './interfaceMain.vue'

  export default {
    name: 'app',
    data() {
      return {
        selected: 0,
        count: 0,
        countTo: 0,
        control: false,
        hint: "",
        bg: {"GET": '#D1EAFF', "POST": '#D1FED3', "PATCH": '#FFE2D2', "DELETE": '#FFD1D1', "PUT": "#F0E0CA"}
      }
    },
    watch: {
      selected: function (newSelected) {
        //  this.$store.mutations.switch({},newSelected)
        this.count = 0, this.countTo = 0;
        /* 初始化 */
        this.$store.commit('switch', newSelected)
      }
    },
    methods: {
      PromptPopUpShow: function (hint) {
        this.hint = hint;
        this.control = true;
      },
      PromptPopUpHide: function () {
        this.hint = "";
        this.control = false;
      },
      ...mapMutations(['switch']),
    },
    components: {interfaceMain},
    computed: {
      bycdaoLeftHead() {
        return this.$store.state.bycdaoLeftHead.data
      },
      leftDropDownBoxContent() {
        return this.$store.state.leftDropDownBoxContent.data;
      },
      bycdaoCategory() {
        let current = [];
        for (let i in this.$store.state.leftDropDownBoxContent.data.paths) {
          for (let n in this.$store.state.leftDropDownBoxContent.data.paths[i]) {
            let count = this.$store.state.leftDropDownBoxContent.data.paths[i][n].tags[0];
            /* 判断当前数据的name是否与当前激活的接口tags一致:后台接口数据顺序与前台显示不一致，需要通过name判断
             * 对name一致的进行保存
              * */
            if (count == this.leftDropDownBoxContent.tags[this.count].name) {
              current.push([i, n, this.$store.state.leftDropDownBoxContent.data.paths[i][n], this.$store.state.leftDropDownBoxContent.data]);
            }
          }
        }
        return current;
      }
    }
  }
</script>
<style>
  /* 动画 */
  .fade-enter-active, .fade-leave-active {
    transition: opacity 5s;
  }

  .fade-enter, .fade-leave-to {
    opacity: 0;
  }

  /* 提示弹窗 */
  .popUps {
    /*position: fixed;*/
    /*top: 0;*/
    /*left: 0;*/
    /*bottom: 0;*/
    /*right: 0;*/
    align-items: center;
    justify-content: center;
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    z-index: 1;
    background: rgba(55, 58, 71, 0.9);
    /*opacity: 0;*/
    transition: opacity 0.3s;
  }

  .popUps > div {
    width: 50%;
    max-width: 560px;
    min-width: 290px;
    background: #fff;
    padding: 4em;
    text-align: center;
    position: absolute;
    z-index: 5;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  /* select及其下方的接口宽度样式 */
  .bycdao-left {
    width: 230px;
    margin-top: 0px;
    position: fixed;
    height: 100%;
    transition: all 0.2s;
  }

  /* 单选框 */
  .form-control {
    display: block;
    width: 80%;
    margin: 0 auto 10px;
    padding: 4px 6px;
    height: 36px;
  }

  .nav-list {
    margin: 0;
    padding: 0;
  }

  .nav-list > li {
    display: block;
    padding: 0;
    margin: 0;
    border: 0;
    height: 56px;
    line-height: 56px;
    position: relative;
    border-left: 5px solid #fff;
    cursor: pointer;
  }

  .nav-list > li:hover, .nav-list > li.active {
    background-color: #F3F8E4;
    color: #8ABF00;
    border-left: 5px solid #8ABF00;
  }

  .nav-list .fa {
    float: left;
    line-height: 56px;
    margin-left: 18px;
  }

  /* 第一层接口列表名字 */
  .navList-name {
    margin-right: 14px;
  }

  .navList-description {
    padding-right: 40px;
    width: 100px;
    display: inline-block;
    text-align: left;
  }

  .navList-number {
    position: absolute;
    right: 27px;
    top: 16px;
    width: 20px;
    height: 20px;
    background-color: #FF3C43;
    border-radius: 10px;
    text-align: center;
    line-height: 20px;
    color: #fff;
    font-size: 14px;
  }

  /* 接口列表 */
  .bycdao-category {
    padding: 0;
    margin-left: 230px;
    width: 220px;
    margin-top: 0px;
    position: fixed;
    /* background: #337ab7; */
    height: 100%;
    transition: all 0.2s;
  }

  .bycdao-category .categoryLi {
    text-align: left;
    margin-bottom: 10px;
    padding: 5px 10px;
  }

  .categoryLi .categoryLi-type {
    display: block;
    height: 20px;
    line-height: 20px;
  }

  .categoryLi .categoryLi-name {
    display: block;
    height: 20px;
    line-height: 20px;
  }

</style>
