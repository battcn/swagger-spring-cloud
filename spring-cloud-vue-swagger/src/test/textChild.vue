<template>
  <div class="tree-menu">
    <div :style="indent" @click="toggleChildren">{{ item.description }}</div>
    <tree-menu :key="key"
      :depth="depth + 1" v-if="showChildren"
      v-for="(item,key) in properties" :properties="item.properties" :item="item">
    </tree-menu>
  </div>
</template>
<script>
  export default {
    props: ['item', 'properties', 'depth'],
    name: 'tree-menu',
    computed: {
      indent() {
        return {transform: `translate(${this.depth * 50}px)`}
      }
    },
    data() {
      return {showChildren: false}
    },
    methods: {
      toggleChildren() {
        this.showChildren = !this.showChildren;
      }
    }
  }
</script>
<style>
  .Children-enter-active, .Children-leave-active {
    transition: opacity .5s;
  }
  .Children-enter, .Children-leave-to{
    opacity: 0;
  }
</style>
