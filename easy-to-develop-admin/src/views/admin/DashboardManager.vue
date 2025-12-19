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
      <StatCard title="总收入" value="$54,230" :trend="12.5" :icon="Money" color="bg-primary" />
      <StatCard title="总订单" value="12,345" :trend="2.4" :icon="ShoppingCart" color="bg-success" />
      <StatCard title="总用户" value="5,465" :trend="1.6" :icon="User" color="bg-info" v-if="showAdminStats" />
      <StatCard title="活跃用户" value="2,430" :trend="8.2" :icon="User" color="bg-warning" v-if="showAdminStats" />
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
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

use([
  CanvasRenderer,
  BarChart,
  LineChart,
  TitleComponent,
  TooltipComponent,
  GridComponent,
]);

const lineChartOption = {
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
};

const barChartOption = {
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
};

const showAdminStats = ref(false);

onMounted(async () => {
  showAdminStats.value = await checkRole('admin');
});
</script>
