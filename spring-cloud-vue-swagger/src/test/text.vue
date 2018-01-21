<template>
  <div id="app">
    <div v-for="item in traa">
      <tree-menu :depth="0" :properties="item.properties" :item="item"></tree-menu>
    </div>
  </div>
</template>
<script>
  import TreeMenu from './textChild.vue'

  let tree = {
    label: 'root',
    nodes: [
      {
        label: 'item1',
        nodes: [
          {
            label: 'item1.1'
          },
          {
            label: 'item1.2',
            nodes: [
              {
                label: 'item1.2.1'
              }
            ]
          }
        ]
      },
      {
        label: 'item2'
      }
    ]
  }
  let traa = {
    "0": {
      "type": "object",
      "required": {"0": "addresses", "1": "clazz", "2": "id", "3": "name"},
      "properties": {
        "addresses": {
          "type": "object",
          "required": {"0": "name", "1": "state"},
          "properties": {
            "name": {"type": "string", "description": "名称"},
            "state": {"type": "boolean", "example": false, "description": "状态"}
          },
          "description": "地址"
        },
        "clazz": {"type": "array", "description": "班级", "items": {"type": "integer", "format": "int64"}},
        "id": {"type": "integer", "format": "int32", "description": "编号"},
        "name": {"type": "string", "description": "名称"}
      },
      "description": "学生"
    }
  };
  export default {
    data() {
      return {traa: traa}
    },
    components: {
      TreeMenu
    }
  }
</script>
