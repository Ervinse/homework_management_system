//表格数据函数
// 查询作业列表接口
const getHomeworkPageAxios = (currentPage,pageSize,searchValue) => {
    return $axios({
        url: '/homework/page',
        method: 'get',
        params:{
            currentPage: currentPage,
            pageSize: pageSize,
            searchValue: searchValue
        }
    })
}

// 查询作业详情接口
const getHomeworkDetailAxios = (homeworkId) => {
    return $axios({
        url: '/homework',
        method: 'get',
        params: {
            homeworkId: homeworkId
        }
    })
}

// 查询作业答案列表接口
const getHomeworkAnswerPageAxios = (homeworkId) => {
    return $axios({
        url: '/homeworkAnswer/list',
        method: 'get',
        params: {
            homeworkId: homeworkId
        }
    })
}

// 查询作业答案详情接口
const getHomeworkAnswerDetailAxios = (homeworkAnswerId) => {
    return $axios({
        url: '/homeworkAnswer',
        method: 'get',
        params: {
            homeworkAnswerId: homeworkAnswerId
        }
    })
}



//操作接口
//添加作业接口
const addHomeworkAxios = (params) => {
    return $axios({
        url: '/homework',
        method: 'post',
        data: { ...params }
    })
}

//删除作业接口
const deleteHomeworkAxios = (homeworkId) => {
    return $axios({
        url: '/homework',
        method: 'delete',
        params: {
            homeworkId: homeworkId
        }
    })
}

//更新作业答案评分评分
const updateRateAxios = (params) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'post',
        data: { ...params }
    })
}

//删除作业接口
const submitHomeworkAnswerAxios = (params) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'get',
        params: {
            data: { ...params }
        }
    })
}