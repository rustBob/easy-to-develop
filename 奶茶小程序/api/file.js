import http from '@/api/index.js'

const prefix = '/globalApi'

export const upload = (files) => {
    return new Promise((resolve, reject) => {
        let response = {
            code: 200,
            data: [],
        }
        
        if (!files || files.length === 0) {
            resolve(response);
            return;
        }
        
        getUploadUrl(files)
            .then(res => {
                const resFile = res.data?.data || res.data // 兼容可能的返回结构
                if (!resFile || !resFile.uploadUrl) {
                     throw new Error('获取上传URL失败: 返回数据异常');
                }

                uploadFile(resFile.uploadUrl, files[0])
                    .then(uploadRes => {
                        // 兼容 uni.request 返回数组 [err, res] 或 Promise res 对象的情况
                        const statusCode = uploadRes.statusCode || (Array.isArray(uploadRes) && uploadRes[1]?.statusCode);
                        
                        if (statusCode === 200) {
                            const url = resFile.uploadUrl.split('?')[0];
                            // 回调通知后端上传完成
                            uploadCallback(resFile.id, url)
                                .then(callbackRes => {
                                    if (callbackRes && (callbackRes.data?.code === 200 || callbackRes.code === 200)) {
                                        response.data.push(url);
                                        resolve(response);
                                    } else {
                                        console.error('上传回调失败', callbackRes);
                                        // 即使回调失败，文件可能已上传成功，但业务逻辑可能需要回调成功。
                                        // 这里暂且认为失败
                                        response.code = 999;
                                        response.msg = '上传回调失败';
                                        resolve(response);
                                    }
                                })
                                .catch(err => {
                                    console.error('上传回调异常:', err);
                                    response.code = 999;
                                    response.msg = '上传回调异常';
                                    resolve(response);
                                });
                        } else {
                            console.error('文件上传云存储失败:', uploadRes);
                            response.code = 999;
                            response.msg = `文件上传失败，状态码: ${statusCode}`;
                            resolve(response);
                        }
                    })
                    .catch(err => {
                        console.error('上传文件过程异常:', err);
                        response.code = 999;
                        response.msg = '上传文件过程异常';
                        resolve(response);
                    });
            })
            .catch(err => {
                console.error('获取上传URL异常:', err);
                response.code = 999;
                response.msg = '获取上传URL异常';
                resolve(response);
            });
    });
}

export const getUploadUrl = (files) => {
    const file = files[0]
    const filePath = file.path || file.tempFilePath;
    return http.post(prefix + '/files/getUploadUrl', {
        name: file.name.split('.')[0],
        type: filePath.split('.').pop(),
        size: file.size,
        extType: filePath.split('.').pop(),
        isPublic: 1
    })
}

// 上传文件
export const uploadFile = async ( url, file ) => {
    return uni.request({
        url: url,
        method: 'PUT',
        data: await fileToArrayBuffer(file),
        header: {
            'Content-Type': 'application/octet-stream' // 显式指定流类型，防止默认 json
        }
    })
}

export const uploadCallback = (id, url) => {
    // 修正参数传递结构，假设后端需要 id 和 url
    return http.post(prefix + `/files/callback`, {id: id, url: url})
}

export const deleteFile = (id) => {
    return http.delete(prefix + `/files`, {id: id})
}

function fileToArrayBuffer(file) {
    return new Promise((resolve, reject) => {
        if (!file) {
            console.log('未选择文件');
            reject(new Error('未选择文件'));
            return;
        }

        // #ifdef H5
        const reader = new FileReader();
        reader.onload = function (e) {
            resolve(e.target.result);
        };
        reader.onerror = function () {
            console.error('文件读取错误:', reader.error);
            reject(reader.error);
        };
        reader.readAsArrayBuffer(file);
        // #endif

        // #ifndef H5
        // 小程序环境使用 uni.getFileSystemManager
        const fs = uni.getFileSystemManager();
        fs.readFile({
            filePath: file.path || file.tempFilePath, // 兼容不同文件对象的路径属性
            success: (res) => {
                resolve(res.data); // res.data 就是 ArrayBuffer
            },
            fail: (err) => {
                console.error('文件读取错误:', err);
                reject(err);
            }
        });
        // #endif
    });
}