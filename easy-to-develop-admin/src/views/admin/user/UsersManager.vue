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
          <el-radio-group v-model="form.roleId">
            <el-radio v-for="role in roles" :key="role.key" :label="role.id">{{ role.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </template>

    <template #rowAction="{ row }">
      <el-dropdown-item @click="checkCoupon(row)" >查看优惠券</el-dropdown-item>
      <el-dropdown-item @click="checkAddress(row)" >查看地址</el-dropdown-item>
    </template>
  </Table>

  <el-dialog v-model="couponDialogVisible" title="用户优惠券" width="680px">
    <div style="margin-bottom:12px; display:flex; gap:12px; align-items:center;">
      <el-select v-model="selectedCouponId" placeholder="选择优惠券" style="width:240px">
        <el-option v-for="c in coupons" :key="c.id" :label="c.name" :value="String(c.id)" />
      </el-select>
      <el-button type="primary" @click="addCoupon">添加</el-button>
    </div>
    <el-table :data="couponList" size="small" border>
      <el-table-column prop="name" label="名称" align="center" />
      <el-table-column prop="type" label="类型" align="center" />
      <el-table-column prop="value" label="面值" align="center" />
      <el-table-column prop="minAmount" label="最低金额" align="center" />
      <el-table-column label="操作" align="center" width="120">
        <template #default="{ row }">
          <el-button type="danger" size="small" @click="deleteCoupon(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <el-button @click="couponDialogVisible=false">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { globalApi } from '@/api/global';
import Table from '@/components/TableComponent.vue'
import { h, onMounted, ref } from 'vue'
import { ElTag, ElDialog } from 'element-plus';
import { useRouter } from 'vue-router';

const router = useRouter();

const roles = ref([])
const coupons = ref([])
const couponDialogVisible = ref(false)
const couponList = ref([])
const currentUser = ref(null)
const selectedCouponId = ref(null)


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
  globalApi['roles'].get(null, null, (res) => {
    roles.value = res.map(role => ({
      id: String(role.id),
      name: role.name,
      key: role.key
    }))
  }, null)

  globalApi['coupons'].get(null,null, (res) => {
    coupons.value = res
  }, null)
})

const checkCoupon = (row) => {
  currentUser.value = row
  couponList.value = Array.isArray(row.couponList) ? JSON.parse(JSON.stringify(row.couponList)) : []
  couponDialogVisible.value = true
}

const addCoupon = () => {
  if (!selectedCouponId.value) return
  const c = coupons.value.find(e => String(e.id) === String(selectedCouponId.value))
  if (!c) return

  globalApi['user-coupons'].add(null, {
    userId: String(currentUser.value.id),
    couponId: String(selectedCouponId.value)
  }, () => {
    couponList.value.push({
      id: String(c.id),
      name: c.name,
      type: c.type,
      value: c.value,
      minAmount: c.minAmount,
      createTime: c.createTime || new Date().toISOString(),
      updateTime: c.updateTime || new Date().toISOString()
    })
    selectedCouponId.value = null
  }, null)
}

const deleteCoupon = (row) => {
  globalApi['user-coupons'].update(null, {
    userId: String(currentUser.value.id),
    couponId: String(row.id)
  }, () => {
    const index = couponList.value.findIndex(e => String(e.id) === String(row.id))
    if (index !== -1) couponList.value.splice(index, 1)
  }, null, 'use')
}

const checkAddress = (row) => {
  router.push(`/admin/users/address/${row.id}`)
}
</script>
