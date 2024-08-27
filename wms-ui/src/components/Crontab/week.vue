<template>
	<el-form size='small'>
		<el-form-item>
			<el-radio v-model='radioValue' :label="1">
				周，允许的通配符[, - * ? / L #]
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="2">
				No 指定
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="3">
				 Cycle从
				<el-select clearable v-model="cycle01">
					<el-option
						v-for="(item,index) of weekList"
						:key="index"
						:label="item.value"
						:value="item.key"
						:disabled="item.key === 1"
					>{{item.value}}</el-option>
				</el-select>
				-
				<el-select clearable v-model="cycle02">
					<el-option
						v-for="(item,index) of weekList"
						:key="index"
						:label="item.value"
						:value="item.key"
						:disabled="item.key < cycle01 && item.key !== 1"
					>{{item.value}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="4">
				Page
				<el-input-number v-model='average01' :min="1" :max="4" /> 周的
				<el-select clearable v-model="average02">
					<el-option v-for="(item,index) of weekList" :key="index" :label="item.value" :value="item.key">{{item.value}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="5">
				本月最后一个
				<el-select clearable v-model="weekday">
					<el-option v-for="(item,index) of weekList" :key="index" :label="item.value" :value="item.key">{{item.value}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="6">
				指定
				<el-select clearable v-model="checkboxList" placeholder="可多选" multiple style="width:100%">
					<el-option v-for="(item,index) of weekList" :key="index" :label="item.value" :value="String(item.key)">{{item.value}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>

	</el-form>
</template>

<script>
export default {
	data() {
		return {
			radioValue: 2,
			weekday: 2,
			cycle01: 2,
			cycle02: 3,
			average01: 1,
			average02: 2,
			checkboxList: [],
			weekList: [
				{
					key: 2,
					value: '一'
				},
				{
					key: 3,
					value: '二'
				},
				{
					key: 4,
					value: '三'
				},
				{
					key: 5,
					value: '四'
				},
				{
					key: 6,
					value: '五'
				},
				{
					key: 7,
					value: '六'
				},
				{
					key: 1,
					value: '日'
				}
			],
			checkNum: this.$options.propsData.check
		}
	},
	name: 'crontab-week',
	props: ['check', 'cron'],
	methods: {
		// 选Button值Change时
		radioChange() {
			if (this.radioValue !== 2 && this.cron.day !== '?') {
				this.$emit('update', 'day', '?', 'week');
			}
			switch (this.radioValue) {
				case 1:
					this.$emit('update', 'week', '*');
					break;
				case 2:
					this.$emit('update', 'week', '?');
					break;
				case 3:
					this.$emit('update', 'week', this.cycleTotal);
					break;
				case 4:
					this.$emit('update', 'week', this.averageTotal);
					break;
				case 5:
					this.$emit('update', 'week', this.weekdayCheck + 'L');
					break;
				case 6:
					this.$emit('update', 'week', this.checkboxString);
					break;
			}
		},

		//  Cycle两个值Change时
		cycleChange() {
			if (this.radioValue == '3') {
				this.$emit('update', 'week', this.cycleTotal);
			}
		},
		// 平均两个值Change时
		averageChange() {
			if (this.radioValue == '4') {
				this.$emit('update', 'week', this.averageTotal);
			}
		},
		// 最近工作日值Change时
		weekdayChange() {
			if (this.radioValue == '5') {
				this.$emit('update', 'week', this.weekday + 'L');
			}
		},
		// checkbox值Change时
		checkboxChange() {
			if (this.radioValue == '6') {
				this.$emit('update', 'week', this.checkboxString);
			}
		},
	},
	watch: {
		'radioValue': 'radioChange',
		'cycleTotal': 'cycleChange',
		'averageTotal': 'averageChange',
		'weekdayCheck': 'weekdayChange',
		'checkboxString': 'checkboxChange',
	},
	computed: {
		// 计算两个 Cycle值
		cycleTotal: function () {
			this.cycle01 = this.checkNum(this.cycle01, 1, 7)
			this.cycle02 = this.checkNum(this.cycle02, 1, 7)
			return this.cycle01 + '-' + this.cycle02;
		},
		// 计算平均用到的值
		averageTotal: function () {
			this.average01 = this.checkNum(this.average01, 1, 4)
			this.average02 = this.checkNum(this.average02, 1, 7)
			return this.average02 + '#' + this.average01;
		},
		// 最近的工作日（格式）
		weekdayCheck: function () {
			this.weekday = this.checkNum(this.weekday, 1, 7)
			return this.weekday;
		},
		// 计算勾选的checkbox值合集
		checkboxString: function () {
			let str = this.checkboxList.join();
			return str == '' ? '*' : str;
		}
	}
}
</script>
