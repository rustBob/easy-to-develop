<template>
  <div class="space-y-6">
    <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
      <div>
        <h1 class="text-2xl font-bold text-gray-900">{{ title }}</h1>
        <p class="text-gray-500 text-sm mt-1">{{ description }}</p>
      </div>
      <slot name="managerAction" />
    </div>

    <!-- 新增 -->
    <el-dialog
      v-model="addObj._visible"
      title="新增"
      width="700px"
      custom-class="rounded-xl shadow-lg"
    >
      <template #header="{ titleId, titleClass }">
        <div class="flex items-center justify-between p-4 border-b border-gray-200">
          <h4 :id="titleId" :class="titleClass" class="text-lg font-semibold text-gray-800">新增</h4>
        </div>
      </template>

      <div class="p-6">
        <el-scrollbar max-height="var(--dialog-scrollbar-max-height)">
          <slot name="addDialogForm" :formRef="el => addObj._ref = el" :form="addObj._form" :rules="addObj._rules" :data="data"/>
        </el-scrollbar>
      </div>

      <template #footer>
        <div class="px-6 py-4 bg-gray-50 border-t border-gray-200 flex justify-end gap-2">
          <el-button @click="addObj._visible = false">取消</el-button>
          <el-button :loading="btnLoading.add" type="primary" @click="addRow()">提交</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 编辑 -->
    <el-dialog
      v-model="updateObj._visible"
      title="编辑"
      width="700px"
      custom-class="rounded-xl shadow-lg"
    >
      <template #header="{ titleId, titleClass }">
        <div class="flex items-center justify-between p-4 border-b border-gray-200">
          <h4 :id="titleId" :class="titleClass" class="text-lg font-semibold text-gray-800">编辑</h4>
        </div>
      </template>

      <div class="p-6">
        <el-scrollbar max-height="var(--dialog-scrollbar-max-height)">
          <slot name="updateDialogForm" :formRef="el => updateObj._ref = el" :form="updateObj._form" :rules="updateObj._rules" :data="data"/>
        </el-scrollbar>
      </div>

      <template #footer>
        <div class="px-6 py-4 bg-gray-50 border-t border-gray-200 flex justify-end gap-2">
          <el-button @click="updateObj._visible = false">取消</el-button>
          <el-button :loading="btnLoading.update" type="primary" @click="editRow()">提交</el-button>
        </div>
      </template>
    </el-dialog>

    <div class="bg-white rounded-xl border border-gray-200 shadow-sm overflow-hidden" v-loading="isLoading">
      <div class="p-4 border-b border-gray-200 flex flex-col sm:flex-row gap-4 justify-between items-center">
        <el-input
          v-model="searchTerm"
          placeholder="搜索"
          :prefix-icon="Search"
          class="w-full sm:w-72"
          v-if="showFilter && filterParam"
        />
        <div class="flex gap-2 w-full sm:w-auto">
          <el-button :icon="Filter" @click="refreshData" v-if="showFilter && filterParam">筛选</el-button>
          <el-button :icon="Refresh" @click="refreshData">刷新</el-button>
          <el-button type="primary" :icon="Plus" @click="addObj._visible = true" v-if="showAdd">添加</el-button>
          <slot name="tableAction" />
        </div>
      </div>

      <!-- 表格 -->
      <el-table :data="data" style="width: 100%" v-bind="tableAttr" show-overflow-tooltip >
        <el-table-column v-for="column in columns" :key="column.prop" v-bind="column" >
          <template v-if="column.template" #default="{ row }">
            <component :is="typeof column.template === 'function' ? column.template(row) : column.template" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="right">
          <template #default="{ row }">
            <el-dropdown>
              <el-button text :icon="MoreFilled" />
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="showUpdateDialog(row)" v-if="showUpdate">编辑</el-dropdown-item>
                  <el-dropdown-item @click="clickDelete(row)" v-if="showDelete">删除</el-dropdown-item>
                  <slot name="rowAction" :row="row" />
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="px-6 py-4 border-t border-gray-200 flex items-center justify-between">
        <el-pagination
          v-model:current-page="pageParams.pageNum"
          :page-size="pageParams.pageSize"
          :total="pageParams.count"
          layout="total, prev, pager, next"
          @current-change="handlePageChange"
        />
      </div>
    </div>

    <!-- 删除确认弹窗 -->
    <el-dialog
      v-model="deleteDiolog"
      title="确认删除"
      width="400px"
      custom-class="rounded-xl shadow-lg"
    >
      <div class="px-6 py-4">
        <p>确认删除选中项吗？</p>
      </div>
      <template #footer>
        <div class="px-6 py-4 bg-gray-50 border-t border-gray-200 flex justify-end gap-2">
          <el-button @click="deleteDiolog = false">取消</el-button>
          <el-button :loading="btnLoading.delete" type="danger" @click="deleteRow">删除</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import {  ElNotification  } from 'element-plus';
import { Search, Filter, Plus, Refresh, MoreFilled } from '@element-plus/icons-vue';

const props = defineProps({
  title: {
    type: String,
    default: '表格'
  },
  description: {
    type: String,
    default: '这是一个表格'
  },
  dataDriver: {
    type: Object,
    required: true
  },
  filter: {
    type: Object,
    required: false
  },
  dataPkNames: {
    type: Array,
    required: true
  },
  columns: {
    type: Array,
    required: true
  },
  addRules: {
    type: Object,
    required: false
  },
  updateRules: {
    type: Object,
    required: false
  },
  preprocessData: {
    type: Function,
    required: false,
    default: (data) => data,
  },
  tableAttr: {
    type: Object,
    default: () => ({})
  },
  showAdd: {
    type: Boolean,
    default: true
  },
  showUpdate: {
    type: Boolean,
    default: true
  },
  showDelete: {
    type: Boolean,
    default: true
  },
  showFilter: {
    type: Boolean,
    default: true
  },
  beforeDelete: {
    type: Function,
    required: false,
    default: () => true,
  },
  afterDelete: {
    type: Function,
    required: false,
    default: () => {},
  },
  filterParam: {
    type: String,
    default: () => ""
  },
  pageSize: {
    type: Number,
    default: 10
  }
})

const isLoading = ref(false);

const data = ref([]);

const searchTerm = ref('');

const addObj = ref({
  _visible: false,
  _form: {},
  _ref: {},
  _rules: props.addRules,
})

const updateObj = ref({
  _visible: false,
  _form: {},
  _origin: {},
  _ref: {},
  _rules: props.updateRules,
})

const selectedRow = ref(null);

const pageParams = ref({
  pageNum: 1,
  pageSize: props.pageSize,
  count: 0
})

const btnLoading = ref({
  delete: false,
  add: false,
  update: false,
});

const deleteDiolog = ref(false);

/**
 * 获取并刷新数据
 */
const refreshData = () => {
  isLoading.value = true;

  const success = res => {
    data.value = props.preprocessData(res.records);
    pageParams.value.count = res.totalRow;
    isLoading.value = false;
  };

  const failure = res => {
    ElNotification.error('获取数据失败:' + res.data.message);
    isLoading.value = false;
  };

  const params = {
    ...props.filter,
    ...pageParams.value,
  };

  props.filterParam && (params[props.filterParam] = searchTerm.value);

  props.dataDriver.list(null, params, success, failure)
};

/**
 * 添加行
 */
const addRow = () => {
  if(btnLoading.value.add) return;
  btnLoading.value.add = true;

  addObj.value._ref.validate(isValid => {
    if(!isValid){
      btnLoading.value.add = false;
      return;
    }

    const success = () => {
      ElNotification.success('添加成功');
      addObj.value._visible = false;
      btnLoading.value.add = false;
      addObj.value._form = {};
      refreshData();
    };

    const failure = res => {
      ElNotification.error('添加失败:' + res.data.message);
      btnLoading.value.add = false;
    };

    props.filter && (addObj.value._form = {
      ...props.filter,
      ...addObj.value._form
    });

    props.dataDriver.add(null, addObj.value._form, success, failure);
  })
}

/**
 * 编辑行
 * @param {Object} row 要编辑的行数据
 */
const editRow = () => {
  if(btnLoading.value.update) return;
  btnLoading.value.update = true;

  updateObj.value._ref.validate(isValid => {
    if(!isValid){
      btnLoading.value.update = false;
      return;
    }

    const success = () => {
      ElNotification.success('更新成功');
      updateObj.value._visible = false;
      btnLoading.value.update = false;
      refreshData();
    };

    const failure = res => {
      ElNotification.error('更新失败:' + res.data.message);
      btnLoading.value.update = false;
    };

    props.dataDriver.update(null, updateObj.value._form, success, failure);
  })
};

/**
 * 显示更新对话框
 * @param {Object} row 要更新的行数据
 */
const showUpdateDialog = (row) => {
  updateObj.value._visible = true;
  updateObj.value._form = { ...row };
  updateObj.value._origin = { ...row };
};
const clickDelete = (row) => {
  selectedRow.value = row;
  deleteDiolog.value = true;
}

/**
 * 删除行
 * @param {Object} row 要删除的行数据
 */
const deleteRow = () => {
  deleteDiolog.value = false;
  btnLoading.value.delete = true;

  if(!props.beforeDelete(selectedRow.value)){
    btnLoading.value.delete = false;
    return;
  }

  const success = () => {
    btnLoading.value.delete = false;
    ElNotification.success('删除成功');
    props.afterDelete(selectedRow.value);
    refreshData();
  };

  const failure = res => {
    btnLoading.value.delete = false;
    ElNotification.error('删除失败:' + res.data.message);
    refreshData();
  };

  props.dataDriver.delete(null, { id: selectedRow.value.id }, success, failure);
};

/**
 * 处理分页组件的页面变化事件。
 * @param {number} page 新的页码。
 */
const handlePageChange = (page) => {
  pageParams.value.pageNum = page;
  refreshData();
};

onMounted(() => {
  refreshData();
});

defineExpose({
  refreshData
});
</script>
