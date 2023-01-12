// 开发环境IP地址
const devIp = 'localhost';

// 生产环境IP地址
const prodIp = '192.168.44.77';

// 存放静态资源的Nginx服务器Host地址
const resNginxHost = `http://${prodIp}:8023`;

// OSS服务器Host地址
const ossHost = 'https://xiao-lesson-bucket.oss-cn-hangzhou.aliyuncs.com/';

// 用户后台服务地址
export const lessonUserBackgroundHost = `http://${devIp}:5277`;

// 弹幕服务地址
export const lessonDanmuServer = `ws://${prodIp}:5555/api/v1/danmu-server/`;

// OSS服务器中用户头像的存储地址
export const ossUserAvatar = `${ossHost}/user-avatar/`;

// Nginx轮播图代理地址
export const nginxBanner = `${resNginxHost}/banner/`;

// Nginx视频封面图代理地址
export const nginxVideoCover = `${resNginxHost}/cover-image/`;

// Nginx视频摘要图代理地址
export const nginxVideoSummary = `${resNginxHost}/summary-image/`;

// Nginx视频文件代理地址
export const nginxVideo = `${resNginxHost}/video/`;

