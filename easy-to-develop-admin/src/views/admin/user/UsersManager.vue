<template>
  <Table
    :data-driver="globalApi['users']"
    :data-pk-names="['id']"
    :columns="columns"
    :title="'用户列表'"
    :description="'用户列表'"
    :addRules="rules"
    :updateRules="updateRules"
    filterParam="username"
  >
    <!-- 添加用户弹窗 -->
    <template #addDialogForm="{ formRef, form, rules }">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" type="password" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
            <el-radio :label="-1">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="enabled">
          <el-radio-group v-model="form.enabled">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-radio-group v-model="form.roleId">
            <el-radio v-for="role in roles" :key="role.key" :label="role.id">{{ role.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </template>

    <!-- 更新用户弹窗 -->
    <template #updateDialogForm="{ formRef, form, rules }">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" type="password" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
            <el-radio :label="-1">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="enabled">
          <el-radio-group v-model="form.enabled">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色" prop="role.id">
          <el-radio-group v-model="form.role.id">
            <el-radio v-for="role in roles" :key="role.key" :label="role.id">{{ role.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </template>

    <template #rowAction="{ row }">
      <el-dropdown-item @click="forceLogout(row)" v-if="row.online" >强制下线</el-dropdown-item>
    </template>
  </Table>
</template>

<script setup>
import { globalApi } from '@/api/global';
import Table from '@/components/TableComponent.vue'
import { h, onMounted, ref } from 'vue'
import { ElNotification, ElTag, ElDialog } from 'element-plus';

const roles = ref([])

const columns = [
  {
    prop: 'username',
    label: '用户名',
    align: 'center',
  },
  {
    prop: 'sex',
    label: '性别',
    align: 'center',
    formatter: (row) => {
      switch (row.sex) {
        case 1: return '男';
        case 0: return '女';
        default: return '未知';
      }
    }
  },
  {
    prop: 'phone',
    label: '手机号',
    align: 'center',
    formatter: (row) => {
      return row.phone ? row.phone : '无';
    }
  },
  {
    prop: 'enabled',
    label: '状态',
    align: 'center',
    template: (row) => {
      return h('div', h(ElTag, { type: row.enabled ? 'success' : 'danger' }, () => row.enabled ? '启用' : '禁用'));
    }
  },
  {
    prop: 'role',
    label: '角色',
    align: 'center',
    formatter: (row) => {
      switch (row.role.key) {
        case "super-admin": return '超级管理员';
        case "admin": return '管理员';
        case "user": return '用户';
        case "driver": return '司机';
        default: return '未知';
      }
    }
  },
  {
    prop: 'online',
    label: '是否在线',
    align: 'center',
    formatter: (row) => {
      return h('div', h(ElTag, { type: row.online ? 'success' : 'danger' }, () => row.online ? '在线' : '不在线'));
    }
  }
]

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: false, message: '请输入手机号', trigger: 'blur' },
    { min: 11, max: 11, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  sex: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  enabled: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ],
  roleId: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

const updateRules = {
  ...rules,
  'role.id': [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}
delete updateRules.roleId;

onMounted(() => {
  globalApi['roles'].get(null, (res) => {
    roles.value = res.map(role => ({
      id: String(role.id),
      name: role.name,
      key: role.key
    }))
  }, null)
})

const forceLogout = (row) => {
  ElDialog.confirm({
    title: '确认强制下线',
    message: '确认强制下线用户 ' + row.username + ' 吗？',
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    globalApi['users'].forceLogout({ id: row.id }, (res) => {
      if (res.code === 200) {
        ElNotification.success({
          title: '成功',
          message: '强制下线成功'
        });
        row.online = 0;
      } else {
        ElNotification.error({
          title: '失败',
          message: res.msg || '强制下线失败'
        });
      }
    }, null)
  }).catch(() => {
    // 取消操作
  });
}
</script>
