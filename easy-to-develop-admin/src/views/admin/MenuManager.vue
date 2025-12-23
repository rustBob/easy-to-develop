<template>
  <Table
    :title="'菜单管理'"
    :description="'这是菜单管理页面'"
    :columns="columns"
    :data-driver="adminApi['menus']"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
    :preprocess-data="toTrees"
    :table-attr="tableAttr"
    :page-size="20"
  >
    <template #managerAction>
      <el-button type="primary" @click="clearMenusCache">清除菜单缓存</el-button>
    </template>

    <!-- 添加表单 -->
    <template #addDialogForm="{ formRef, form, rules, data}">
      <el-form :ref="formRef" :model="form" :rules="rules">
        <el-form-item label="父菜单" prop="parentId">
         <el-cascader
            :options="[{id: 0, name: '顶级菜单', children: []}, ...data]"
            v-model="form.parentId"
            placeholder="请选择菜单"
            @change="value => form.parentId = value[value.length - 1]"
            :props="{value: 'id', label: 'name', checkStrictly: true}"
          />
        </el-form-item>
        <el-form-item label="所属角色" prop="roleId">
          <el-cascader
            :options="[{id: 0, name: '顶级角色', children: []}, ...roles]"
            v-model="form.roleId"
            placeholder="请选择角色"
            @change="value => form.roleId = value[value.length - 1]"
            :props="{value: 'id', label: 'name', checkStrictly: true}"
          />
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <div class="flex items-center gap-2">
            <el-icon :size="25" v-if="form.icon">
              <component :is="form.icon" />
            </el-icon>
            <el-popover
                placement="bottom"
                title="选择图标"
                width="1000"
                trigger="click"
            >
              <template #reference>
                <el-button>选择</el-button>
              </template>
              <el-scrollbar max-height="300px">
                <div class="flex flex-wrap gap-4 p-2">
                  <div
                      v-for="iconKey in Object.keys(iconMap)"
                      :key="iconKey"
                      class="p-2 border rounded-md cursor-pointer hover:bg-gray-100"
                      @click="form.icon = iconKey"
                  >
                    <el-icon :size="25">
                      <component :is="iconMap[iconKey]" />
                    </el-icon>
                  </div>
                </div>
              </el-scrollbar>
            </el-popover>
          </div>
        </el-form-item>
        <el-form-item label="菜单排序" prop="order">
          <el-input-number v-model="form.order" placeholder="请输入排序" style="width: 200px" />
        </el-form-item>
        <el-form-item label="路由路径" prop="path">
          <el-input v-model="form.path" placeholder="请输入路由路径" />
        </el-form-item>
        <el-form-item label="组件路径" prop="component">
          <el-autocomplete
            v-model="form.component"
            placeholder="请输入组件路径"
            :fetch-suggestions="(queryString, cb) => {
              cb([
                { value: '#', label: queryString },
                { value: '/' + queryString, label: queryString },
                { value: '/admin/' + queryString, label: queryString }
              ])
            }"
          />
        </el-form-item>
        <el-form-item label="是否可见" prop="visible">
          <el-radio-group v-model="form.visible">
            <el-radio :label="1">可见</el-radio>
            <el-radio :label="0">不可见</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </template>

    <!-- 修改表单 -->
    <template #updateDialogForm="{ formRef, form, rules, data }">
      <el-form :ref="formRef" :model="form" :rules="rules">
        <el-form-item label="父菜单" prop="parentId">
         <el-cascader
            :options="[{id: 0, name: '顶级菜单', children: []}, ...data]"
            v-model="form.parentId"
            placeholder="请选择菜单"
            @change="value => form.parentId = value[value.length - 1]"
            :props="{value: 'id', label: 'name', checkStrictly: true}"
          />
        </el-form-item>
        <el-form-item label="所属角色" prop="roleId">
          <el-cascader
            :options="[{id: 0, name: '顶级角色', children: []}, ...roles]"
            v-model="form.roleId"
            placeholder="请选择角色"
            @change="value => {
              console.log(value);

              form.roleId = value[value.length - 1]
            }"
            :props="{value: 'id', label: 'name', checkStrictly: true}"
          />
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <div class="flex items-center gap-2">
            <el-icon :size="25" v-if="form.icon">
              <component :is="form.icon" />
            </el-icon>
            <el-popover
                placement="bottom"
                title="选择图标"
                width="1000"
                trigger="click"
            >
              <template #reference>
                <el-button>选择</el-button>
              </template>
              <el-scrollbar max-height="300px">
                <div class="flex flex-wrap gap-4 p-2">
                  <div
                      v-for="iconKey in Object.keys(iconMap)"
                      :key="iconKey"
                      class="p-2 border rounded-md cursor-pointer hover:bg-gray-100"
                      @click="form.icon = iconKey"
                  >
                    <el-icon :size="25">
                      <component :is="iconMap[iconKey]" />
                    </el-icon>
                  </div>
                </div>
              </el-scrollbar>
            </el-popover>
          </div>
        </el-form-item>
        <el-form-item label="菜单排序" prop="order">
          <el-input-number v-model="form.order" placeholder="请输入排序" style="width: 200px" />
        </el-form-item>
        <el-form-item label="路由路径" prop="path">
          <el-input v-model="form.path" placeholder="请输入路由路径" />
        </el-form-item>
        <el-form-item label="组件路径" prop="component">
          <el-autocomplete
            v-model="form.component"
            placeholder="请输入组件路径"
            :fetch-suggestions="(queryString, cb) => {
              cb([
                { value: '#', label: queryString },
                { value: '/' + queryString, label: queryString },
                { value: '/admin/' + queryString, label: queryString }
              ])
            }"
          />
        </el-form-item>
        <el-form-item label="是否可见" prop="visible">
          <el-radio-group v-model="form.visible">
            <el-radio :label="1">可见</el-radio>
            <el-radio :label="0">不可见</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from '@/components/TableComponent.vue'
import { adminApi } from '@/api/admin/index.js'
import { globalApi } from '@/api/global/index.js'
import { h, resolveComponent, ref, onMounted } from 'vue'
import { ElTag, ElIcon } from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import store from '@/store'
import { toTrees } from '@/util/common.js'

const roles = ref([]);

const iconMap = {};

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  iconMap[key] = component;
}

const tableAttr = {
  rowKey: 'id',
}

const columns = [
  {
    prop: 'name',
    label: '菜单名称',
    align: 'left',
  },
  {
    prop: 'order',
    label: '菜单排序',
    align: 'center',
  },
  {
    prop: 'path',
    label: '路由路径',
    align: 'center',
  },
  {
    prop: 'component',
    label: '组件路径',
    align: 'center',
  },
  {
    prop: 'icon',
    label: '图标',
    align: 'center',
    template: (row) => {
      return h(ElIcon, {size: 25}, () => [h(resolveComponent(row.icon))])
    },
  },
  {
    prop: 'visible',
    label: '是否可见',
    align: 'center',
    template: (row) => {
      return h('div', h(ElTag, { type: row.visible ? 'success' : 'danger' }, () => row.visible ? '可见' : '不可见'));
    }
  },
  {
    prop: 'role.name',
    label: '所属角色',
    align: 'center',
    template: (row) => {
      return h('div', h(ElTag, { type: row.role ? 'success' : 'danger' }, () => row.role ? row.role.name : '无'));
    }
  }
]

const rules = {
  name: [
    { required: true, message: '请输入菜单名称', trigger: 'blur' },
  ],
  parentId: [
    { required: true, message: '请输入父菜单ID', trigger: 'blur' },
  ],
  order: [
    { required: true, message: '请输入排序', trigger: 'blur' },
    { type: 'number', min: 0, message: '排序值必须为数字且不能小于0', trigger: 'blur' },
  ],
  path: [
    { required: true, message: '请输入路由路径', trigger: 'blur' },
  ],
  component: [
    { required: true, message: '请输入组件路径', trigger: 'blur' },
  ],
  icon: [
    { required: true, message: '请输入图标', trigger: 'blur' },
  ],
  visible: [
    { required: true, message: '请选择是否可见', trigger: 'change' },
  ],
  roleId: [
    { required: true, message: '请选择所属角色', trigger: 'change' },
  ]
}

onMounted(() => {
  globalApi.roles.get(null, (res) => {
    roles.value = toTrees(res);
  })
});

const clearMenusCache = () => {
  store.remove('menus');
  location.reload();
}
</script>
