<template>
  <Table
    title="角色管理"
    description="管理系统访问和用户角色。"
    :data-driver="globalApi['roles']"
    :data-pk-names="['id']"
    :columns="columns"
    :add-rules="rules"
    :update-rules="rules"
    :preprocess-data="toTrees"
    :table-attr="tableAttr"
  >
    <template #addDialogForm="{ formRef, form, rules, data }">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="父角色" prop="parentId">
         <el-cascader
            :options="[{id: 0, name: '顶级角色', children: []}, ...data]"
            v-model="form.parentId"
            placeholder="请选择角色"
            @change="value => form.parentId = value[value.length - 1]"
            :props="{value: 'id', label: 'name', checkStrictly: true}"
          />
        </el-form-item>
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色键值" prop="key">
          <el-input v-model="form.key" placeholder="请输入角色键值" />
        </el-form-item>
      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules, data }">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="父角色" prop="parentId">
         <el-cascader
            :options="[{id: 0, name: '顶级角色', children: []}, ...data]"
            v-model="form.parentId"
            placeholder="请选择角色"
            @change="value => form.parentId = value[value.length - 1]"
            :props="{value: 'id', label: 'name', checkStrictly: true}"
          />
        </el-form-item>
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色键值" prop="key">
          <el-input v-model="form.key" placeholder="请输入角色键值" />
        </el-form-item>
      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";
import { toTrees } from "@/util/common.js";

const tableAttr = {
  rowKey: 'id',
}

const columns = [
  {
    prop: 'name',
    label: '角色名称',
    align: 'left',
  },
  {
    prop: 'key',
    label: '角色键值',
    align: 'center',
  },
]

const rules = {
  parentId: [
    { required: true, message: '请选择父角色', trigger: 'change' },
  ],
  name: [
    { required: true, message: '请输入角色名称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  key: [
    { required: true, message: '请输入角色键值', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ]
}

</script>
