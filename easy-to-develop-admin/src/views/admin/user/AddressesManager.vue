<template>
  <Table
    :data-driver="globalApi['locations']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
    :filter="{ userId: props.userId }"
  >
    <template #addDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="收货人" prop="name">
          <el-input v-model="form.name" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="所在区域" prop="position">
          <el-input v-model="form.position" placeholder="请输入所在区域" />
        </el-form-item>
        <el-form-item label="详细地址" prop="detail">
          <el-input v-model="form.detail" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="标签" prop="tag">
          <el-input v-model="form.tag" placeholder="请输入标签" />
        </el-form-item>
        <el-form-item label="默认地址" prop="isDefault">
          <el-radio-group v-model="form.isDefault">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="收货人" prop="name">
          <el-input v-model="form.name" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="所在区域" prop="position">
          <el-input v-model="form.position" placeholder="请输入所在区域" />
        </el-form-item>
        <el-form-item label="详细地址" prop="detail">
          <el-input v-model="form.detail" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="标签" prop="tag">
          <el-input v-model="form.tag" placeholder="请输入标签" />
        </el-form-item>
        <el-form-item label="默认地址" prop="isDefault">
          <el-radio-group v-model="form.isDefault">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";
import { defineProps } from 'vue'

const props = defineProps({
  userId: {
    type: String,
    default: ''
  }
})


const columns = [
  { prop: 'name', label: '收货人', align: 'center' },
  { prop: 'gender', label: '性别', align: 'center', formatter: (row) => row.gender === 1 ? '男' : '女' },
  { prop: 'phone', label: '手机号', align: 'center' },
  { prop: 'position', label: '所在区域', align: 'center' },
  { prop: 'detail', label: '详细地址', align: 'center' },
  { prop: 'tag', label: '标签', align: 'center' },
  { prop: 'isDefault', label: '默认地址', align: 'center', formatter: (row) => row.isDefault === 1 ? '是' : '否' },
]

const rules = {
  name: [
    { required: true, message: '请输入收货人', trigger: 'blur' },
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { min: 11, max: 11, message: '手机号长度为 11 个字符', trigger: 'blur' }
  ],
  position: [
    { required: true, message: '请输入所在区域', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  detail: [
    { required: true, message: '请输入详细地址', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  tag: [
    { required: true, message: '请输入标签', trigger: 'blur' },
  ],
  isDefault: [
    { required: true, message: '请选择默认地址', trigger: 'change' }
  ]
}
</script>
