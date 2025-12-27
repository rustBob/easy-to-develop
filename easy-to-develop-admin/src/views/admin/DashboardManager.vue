<template>
  <div class="space-y-6">
    <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
      <div>
        <h1 class="text-2xl font-bold text-gray-900">仪表盘概览</h1>
        <p class="text-gray-500 text-sm mt-1">欢迎回来，这是今天的情况。</p>
      </div>
      <div class="flex gap-2">
        <el-button>下载报告</el-button>
        <el-button type="primary">创建活动</el-button>
      </div>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6" :class="showAdminStats ? 'lg:grid-cols-4' : 'lg:grid-cols-2'">
      <StatCard title="总收入" :value="'¥' + stats.totalIncome" :trend="100" :icon="Money" color="bg-primary" />
      <StatCard title="总订单" :value="stats.orderCount" :trend="100" :icon="ShoppingCart" color="bg-success" />
      <StatCard title="总用户" :value="stats.userCount" :trend="100" :icon="User" color="bg-info" v-if="showAdminStats" />
      <StatCard title="活跃用户" :value="stats.activeUserCount" :trend="100" :icon="User" color="bg-warning" v-if="showAdminStats" />
    </div>

    <div class="grid grid-cols-1 gap-6" :class="showAdminStats ? 'lg:grid-cols-2' : 'lg:grid-cols-1'">
      <div class="bg-white p-6 rounded-xl border border-gray-100 shadow-sm">
        <h3 class="text-lg font-bold text-gray-900 mb-6">收入分析</h3>
        <div class="h-80">
          <v-chart class="w-full h-full" :option="lineChartOption" autoresize />
        </div>
      </div>

      <div class="bg-white p-6 rounded-xl border border-gray-100 shadow-sm" v-if="showAdminStats">
        <h3 class="text-lg font-bold text-gray-900 mb-6">用户活动</h3>
        <div class="h-80">
          <v-chart class="w-full h-full" :option="barChartOption" autoresize />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import StatCard from '@/components/StatCardComponent.vue';
import { Money, User, ShoppingCart } from '@element-plus/icons-vue';
import { use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { BarChart, LineChart } from 'echarts/charts';
import { TitleComponent, TooltipComponent, GridComponent } from 'echarts/components';
import VChart from 'vue-echarts';
import { checkRole } from '@/util/perssion';
import { adminApi } from '@/api/admin';
import Store from '@/store';

use([
  CanvasRenderer,
  BarChart,
  LineChart,
  TitleComponent,
  TooltipComponent,
  GridComponent,
]);

const lineChartOption = ref({
  grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
  xAxis: {
    type: 'category',
    data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月'],
    axisLine: { show: false },
    axisTick: { show: false },
  },
  yAxis: {
    type: 'value',
    axisLabel: { formatter: '${value}' },
    splitLine: { lineStyle: { type: 'dashed' } },
  },
  tooltip: { trigger: 'axis' },
  series: [
    {
      data: [4000, 3000, 2000, 2780, 1890, 2390, 3490],
      type: 'line',
      smooth: true,
      symbol: 'circle',
      symbolSize: 8,
      lineStyle: { width: 3, color: '#409EFF' },
      itemStyle: { color: '#409EFF', borderColor: '#fff', borderWidth: 2 },
    },
  ],
});

const barChartOption = ref({
  grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
  xAxis: {
    type: 'category',
    data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月'],
    axisLine: { show: false },
    axisTick: { show: false },
  },
  yAxis: {
    type: 'value',
    splitLine: { lineStyle: { type: 'dashed' } },
  },
  tooltip: { trigger: 'axis' },
  series: [
    {
      data: [2400, 1398, 9800, 3908, 4800, 3800, 4300],
      type: 'bar',
      barWidth: '30%',
      itemStyle: {
        borderRadius: [4, 4, 0, 0],
        color: '#67C23A',
      },
    },
  ],
});

const showAdminStats = ref(false);
const stats = ref({
  totalIncome: 0,
  orderCount: 0,
  userCount: 0,
  activeUserCount: 0,
});

onMounted(async () => {
  showAdminStats.value = await checkRole('super-admin');

  let params = {};

  if(Store.get('store')) params.storeId = Store.get('store').id;

  await adminApi['stats'].get(params, (res) => {
    // 绑定基础统计数据
    const totalIncome = res.find(item => item.name === 'totalIncome');
    const orderCount = res.find(item => item.name === 'orderCount');
    const userCount = res.find(item => item.name === 'userCount');
    const activeUserCount = res.find(item => item.name === 'activeUserCount');

    if (totalIncome && totalIncome.value) stats.value.totalIncome = totalIncome.value[0];
    if (orderCount && orderCount.value) stats.value.orderCount = orderCount.value[0];
    if (userCount && userCount.value) stats.value.userCount = userCount.value[0];
    if (activeUserCount && activeUserCount.value) stats.value.activeUserCount = activeUserCount.value[0];

    // 生成最近12个月的月份标签
    const months = [];
    const date = new Date();
    for (let i = 11; i >= 0; i--) {
      const d = new Date(date.getFullYear(), date.getMonth() - i, 1);
      const month = d.getMonth() + 1;
      const year = d.getFullYear();
      months.push(`${year}-${month < 10 ? '0' + month : month}`);
    }
    lineChartOption.value.xAxis.data = months;
    barChartOption.value.xAxis.data = months;

    // 更新数据
    const monthIncomeData = res.find(item => item.name === 'monthIncome');
    if (monthIncomeData && Array.isArray(monthIncomeData.value)) {
       lineChartOption.value.series[0].data = monthIncomeData.value;
    }

    const monthOrderCountData = res.find(item => item.name === 'monthOrderCount');
    if (monthOrderCountData && Array.isArray(monthOrderCountData.value)) {
       barChartOption.value.series[0].data = monthOrderCountData.value;
    }
  });
});
</script>
