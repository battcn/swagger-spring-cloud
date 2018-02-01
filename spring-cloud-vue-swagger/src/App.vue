<template>
  <div id="app">
    <list></list>
    <!--<interfaceMain></interfaceMain>-->
  </div>
</template>


<script type="text/ecmascript-6">
  import 'normalize.css'
  import list from './page/interfaceList.vue'
  import interfaceMain from './page/interfaceMain.vue';
  export default {
    name: 'app',
    components: {list, interfaceMain}
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif,微软雅黑;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
  }
  html,body{padding:0;}
  ul{margin:0;padding: 0;}
  li{list-style: none;}

</style>
