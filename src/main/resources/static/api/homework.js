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
//
const getHomeworkPageByCourseIdAxios = (currentPage,pageSize,courseId) => {
    return $axios({
        url: '/homework/page/course',
        method: 'get',
        params:{
            currentPage: currentPage,
            pageSize: pageSize,
            courseId: courseId
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

// 查询指定学生的作业评分接口
const getHomeworkRateAxios = (homeworkId,studentId) => {
    return $axios({
        url: '/homeworkAnswer/rate',
        method: 'get',
        params: {
            homeworkId: homeworkId,
            studentId: studentId
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

//更新作业答案评分
const updateRateAxios = (params) => {
    return $axios({
        url: '/homeworkAnswer/updateRate',
        method: 'put',
        data: { ...params }
    })
}

//提交作业答案接口
const submitHomeworkAnswerAxios = (params) => {
    return $axios({
        url: '/homeworkAnswer',
        method: 'post',
        data: { ...params }
    })
}