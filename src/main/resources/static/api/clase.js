//表格数据函数
// 查询班级列表接口
const getClasePageAxios = () => {
    return $axios({
        url: '../../json/clase/clase_page_success.json',
        method: 'get',
    })
}

// 查询班级详情接口
const getClaseDetailAxios = (claseId) => {
    return $axios({
        url: '../../json/clase/clase_select_success.json',
        method: 'get',
        params: {
            claseId: claseId
        }
    })
}

// 查询班级搜索列表接口
const getClasePageSearchAxios = (params) => {
    return $axios({
        url: '../../json/clase/clase_page_search_success.json',
        method: 'get',
        params: {params}
    })
}


//操作接口
//添加班级接口
const addClaseAxios = (params) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'post',
        data: {...params}
    })
}

//编辑班级接口
const editClaseAxios = (params) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'post',
        data: {...params}
    })
}

//删除班级接口
const deleteClaseAxios = (claseId) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'get',
        params: {
            claseId: claseId
        }
    })
}