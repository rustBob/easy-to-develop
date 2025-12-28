<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
    body {
        font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
        background-color: #f8f8f8;
    }
    
    view, text, button, image, scroll-view {
        box-sizing: border-box;
    }
    
    ::-webkit-scrollbar {
        display: none;
        width: 0;
        height: 0;
    }
</style>
