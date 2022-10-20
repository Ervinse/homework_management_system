//表格数据函数
// 查询作业列表接口
const getHomeworkPageAxios = (params) => {
    return $axios({
        url: '../../json/homework/homework_page_success.json',
        method: 'get',
        params: {params}
    })
}

// 查询作业详情接口
const getHomeworkDetailAxios = (claseId) => {
    return $axios({
        url: '../../json/homework/homework_select_success.json',
        method: 'get',
        params: {
            claseId: claseId
        }
    })
}

// 查询作业答案列表接口
const getHomeworkAnswerPageAxios = (homeworkId) => {
    return $axios({
        url: '../../json/homeworkAnswer/homeworkAnswer_page_success.json',
        method: 'get',
        params: {
            homeworkId: homeworkId
        }
    })
}

// 查询作业答案详情接口
const getHomeworkAnswerDetailAxios = (homeworkAnswerId) => {
    return $axios({
        url: '../../json/homeworkAnswer/homeworkAnswer_select_success.json',
        method: 'get',
        params: {
            homeworkAnswerId: homeworkAnswerId
        }
    })
}


// 查询班级搜索列表接口
const getHomeworkPageSearchAxios = (params) => {
    return $axios({
        url: '../../json/homework/homework_page_search_success.json',
        method: 'get',
        params: {params}
    })
}


//操作接口
//添加作业接口
const addHomeworkAxios = (params) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'post',
        data: { ...params }
    })
}

//删除作业接口
const deleteHomeworkAxios = (homeworkId) => {
    return $axios({
        url: '../../json/common/success.json',
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