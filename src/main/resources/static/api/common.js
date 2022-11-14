//候选数据查询接口
// 查询老师集合接口
const getTeacherListAxios = () => {
    return $axios({
        url: '/teacher/list',
        method: 'get',
    })
}

// 查询班级集合接口
const getClaseListAxios = () => {
    return $axios({
        url: '/clase/list',
        method: 'get',
    })
}

// 查询学生集合接口
const getStudentListAxios = () => {
    return $axios({
        url: '/student/list',
        method: 'get',
    })
}

// 查询课程集合接口
const getCourseListAxios = () => {
    return $axios({
        url: '/course/list',
        method: 'get',
    })
}

//图片相关接口
//图片上传接口
const imageUploadUrl = '/common/uploadImage';
//文件上传接口
const fileUploadUrl = '/common/uploadFile';

//移除图片接口
const removeImageAxios = (imageName) => {
    return $axios({
        url: '/common/deleteImage',
        method: 'delete',
        params: {
            imageName: imageName
        }
    })
}

//移除文件接口
const removeFileAxios = (fileName) => {
    return $axios({
        url: '/common/deleteFile',
        method: 'delete',
        params: {
            fileName: fileName
        }
    })
}

const logoutAxios = () => {
    return $axios({
        url: '/login/logout',
        method: 'post',
    })
}


//模拟数据请求接口
//生成全部模拟数据请求
const generateFullSimulatedDataAxios = () => {
    return $axios({
        url: '/common/fullSimulatedData',
        method: 'post',
    })
}

//生成部分模拟数据
const generateBasicSimulatedDataAxios = () => {
    return $axios({
        url: '/common/basicSimulatedData',
        method: 'post',
    })
}