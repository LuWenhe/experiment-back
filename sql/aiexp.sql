/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : aiexp

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 11/05/2023 18:56:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for banners
-- ----------------------------
DROP TABLE IF EXISTS `banners`;
CREATE TABLE `banners`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `banner_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of banners
-- ----------------------------
INSERT INTO `banners` VALUES (1, 'http://localhost:8081/9ca47aff-31ed-466f-a06c-aa06eaa9d2c4.png');
INSERT INTO `banners` VALUES (2, 'http://localhost:8081/10eb1425-bf77-4e7b-943b-fb8d664c4468.png');
INSERT INTO `banners` VALUES (3, 'http://localhost:8081/7693e214-3ecc-4dee-ab18-edb63d10202a.png');

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter`  (
  `chapter_id` int(11) NOT NULL AUTO_INCREMENT,
  `chapter_no` int(11) NULL DEFAULT NULL,
  `chapter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mp4` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ppt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exp_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `guide_book` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `lessonId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`chapter_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chapter
-- ----------------------------
INSERT INTO `chapter` VALUES (5, 1, '第一章 机器学习基础', '机器学习基础', NULL, NULL, NULL, NULL, 6);
INSERT INTO `chapter` VALUES (12, 1, 'ConvLSTM', 'ConvLSTM', NULL, NULL, NULL, NULL, 8);
INSERT INTO `chapter` VALUES (14, 1, '计算机视觉基础', '计算机视觉基础', NULL, NULL, NULL, NULL, 7);
INSERT INTO `chapter` VALUES (15, 1, '卷积神经网络（Convolutional Neural Networks, CNN）', '卷积神经网络（Convolutional Neural Networks, CNN）', NULL, NULL, NULL, NULL, 5);

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `teacher_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES (1, '班级1', 2);
INSERT INTO `clazz` VALUES (2, '班级2', 2);
INSERT INTO `clazz` VALUES (3, '班级3', 2);
INSERT INTO `clazz` VALUES (4, '班级4', 4);
INSERT INTO `clazz` VALUES (5, '班级5', 4);
INSERT INTO `clazz` VALUES (6, '班级6', 5);

-- ----------------------------
-- Table structure for lesson
-- ----------------------------
DROP TABLE IF EXISTS `lesson`;
CREATE TABLE `lesson`  (
  `lessonId` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `difficulty` int(11) NULL DEFAULT NULL,
  `learn_time` double(11, 0) NULL DEFAULT NULL,
  `learn_credit` double(11, 0) NULL DEFAULT NULL,
  `suitablePerson` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `canLearn` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `teacher_id` int(11) NULL DEFAULT NULL,
  `teacher_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`lessonId`) USING BTREE,
  INDEX `lesson_name_index`(`lesson_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lesson
-- ----------------------------
INSERT INTO `lesson` VALUES (1, 'TensorFlow', 'http://localhost:8081/3e0c8303-52f3-4098-a167-32bdfbc4ffd2.png', 2, 22, 2, '研究生', '', '深度学习最早兴起于图像识别，但在短短几年时间内，深度学习推广到了机器学习的各个领域。如今深度学习已经应用于计算机视觉、自然语言处理、情感分析、医学自动诊断等各个领域。\n\n', NULL, 'teacher1', NULL);
INSERT INTO `lesson` VALUES (2, 'PyTorch教程', 'http://localhost:8081/2c2f312d-9ee4-4306-8c18-960046c32302.png', 2, 22, 2, '研究生', '', '深度学习框架', NULL, 'nxd', NULL);
INSERT INTO `lesson` VALUES (3, '数据挖掘', 'http://localhost:8081/20211214210608.png', 3, 22, 2, '研究生', '', '', NULL, 'teacher1', NULL);
INSERT INTO `lesson` VALUES (5, '深度学习雷雨大风分类与识别模块', 'http://localhost:8081/20211214210408.png', 2, 22, 2, '研究生', '基于深度学习的短临预报', '利用深度学习方法实现对雷暴大风灾害性天气的识别、预警，以地面自动站出现7级风作为出现灾害性雷暴大风天气的判据，建立一套雷暴大风实时识别、落区预报、落区检验于一体的综合系统。', NULL, 'nxd', NULL);
INSERT INTO `lesson` VALUES (6, '机器学习', 'http://localhost:8081/f707c3bc-60d9-43a0-920a-e73f15b6e3a7.png', 2, 22, 2, '研究生', '', '机器学习是一门多领域交叉学科，涉及概率论、统计学、逼近论、凸分析、算法复杂度理论等多门学科。专门研究计算机怎样模拟或实现人类的学习行为，以获取新的知识或技能，重新组织已有的知识结构使之不断改善自身的性能。\n它是人工智能核心，是使计算机具有智能的根本途径。', NULL, 'nxd', NULL);
INSERT INTO `lesson` VALUES (7, '计算机视觉', 'http://localhost:8081/4ce3e6b3-f15e-4c12-b3c6-4238aabebde9.png', 2, 22, 2, '研究生', '知识', '计算机视觉', NULL, 'teacher1', NULL);
INSERT INTO `lesson` VALUES (8, '气象雷达智能外推', 'http://localhost:8081/4b68f5fc-649e-4627-8221-668f178e9232.png', 2, 22, 2, '研究生', '基于ConvLSTM Encoder-Decoder的雷达回波外推', '基于深度学习算法模型对气象雷达数据进行外推，雷达数据是一种典型的时空序列数据，短临降水预测属于典型的时空序列预测任务。由于雷达数据不仅具有时间上的连续性，还具有空间性，需要同时进行时间和空间信息的学习，是一种高度非线性和随机性的问题，而深度学习可以通过对非线性系统进行建模，来对降水格点数据进行合理外推，并能够在一定程度上解决随机性的问题，从而实现较为准确的短临降水预报。\n![f444aab5415c42c68c03a6c981a4fcc7.jpg](http://localhost:8081/a4edaa61-db35-4ea5-ad2f-695bbe42d7d4.jpg)\nhello', NULL, 'nxd', NULL);

-- ----------------------------
-- Table structure for lesson_tag
-- ----------------------------
DROP TABLE IF EXISTS `lesson_tag`;
CREATE TABLE `lesson_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lessonId` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lesson_tag
-- ----------------------------
INSERT INTO `lesson_tag` VALUES (22, 7, 1);
INSERT INTO `lesson_tag` VALUES (29, 6, 1);
INSERT INTO `lesson_tag` VALUES (30, 2, 1);
INSERT INTO `lesson_tag` VALUES (31, 1, 1);
INSERT INTO `lesson_tag` VALUES (32, 3, 1);
INSERT INTO `lesson_tag` VALUES (37, 5, 2);
INSERT INTO `lesson_tag` VALUES (39, 8, 2);

-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of province
-- ----------------------------
INSERT INTO `province` VALUES (1, '北京');
INSERT INTO `province` VALUES (2, '天津');
INSERT INTO `province` VALUES (3, '河北');
INSERT INTO `province` VALUES (4, '山西');
INSERT INTO `province` VALUES (5, '内蒙古');
INSERT INTO `province` VALUES (6, '辽宁');
INSERT INTO `province` VALUES (7, '吉林');
INSERT INTO `province` VALUES (8, '黑龙江');
INSERT INTO `province` VALUES (9, '上海');
INSERT INTO `province` VALUES (10, '江苏');
INSERT INTO `province` VALUES (11, '浙江');
INSERT INTO `province` VALUES (12, '安徽');
INSERT INTO `province` VALUES (13, '福建');
INSERT INTO `province` VALUES (14, '江西');
INSERT INTO `province` VALUES (15, '山东');
INSERT INTO `province` VALUES (16, '河南');
INSERT INTO `province` VALUES (17, '湖北');
INSERT INTO `province` VALUES (18, '湖南');
INSERT INTO `province` VALUES (19, '广东');
INSERT INTO `province` VALUES (20, '广西');
INSERT INTO `province` VALUES (21, '海南');
INSERT INTO `province` VALUES (22, '重庆');
INSERT INTO `province` VALUES (23, '四川');
INSERT INTO `province` VALUES (24, '贵州');
INSERT INTO `province` VALUES (25, '云南');
INSERT INTO `province` VALUES (26, '西藏');
INSERT INTO `province` VALUES (27, '陕西');
INSERT INTO `province` VALUES (28, '甘肃');
INSERT INTO `province` VALUES (29, '青海');
INSERT INTO `province` VALUES (30, '宁夏');
INSERT INTO `province` VALUES (31, '新疆');

-- ----------------------------
-- Table structure for sidemenu
-- ----------------------------
DROP TABLE IF EXISTS `sidemenu`;
CREATE TABLE `sidemenu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `index` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sidemenu
-- ----------------------------
INSERT INTO `sidemenu` VALUES (1, 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for son_chapter
-- ----------------------------
DROP TABLE IF EXISTS `son_chapter`;
CREATE TABLE `son_chapter`  (
  `son_id` int(11) NOT NULL AUTO_INCREMENT,
  `son_no` double(11, 3) NULL DEFAULT NULL,
  `son_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mp4` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ppt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exp_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exp_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `guide_book` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `chapter_id` int(11) NULL DEFAULT NULL,
  `lessonId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`son_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of son_chapter
-- ----------------------------
INSERT INTO `son_chapter` VALUES (35, 1.100, 'ConvLSTM（一）', 'ConvLSTM', 'http://localhost:4040/f37cadbd-fcc5-4d95-bbde-2256f687651e.mp4', 'http://localhost:4040/e2ce467b-ccc8-4df2-8f91-5a3876de452f.pptx', 'Jupyter', NULL, '#  RNN -> LSTM -> ConvLSTM (一)\n[1 RNN](#1)    \n  &ensp; [1.1 结构图](#1.1)  \n  &ensp; [1.2 公式](#1.2)   \n  &ensp; [1.3 代码](#1.3)   \n[2 LSTM](#2)  \n  &ensp; [2.1 单元结构图与公式讲解](#2.1)   \n  &ensp; [2.2 代码](#2.2)    \n[3 练习与思考](#3)  \n<a id=\'1\'></a>\n## 1 RNN\n  &ensp;&ensp; 最为原始的循环神经网络，本质就是全连接网络，只是为了考虑过去的信息，输出不仅取决于当前输入，还取决于之前的信息，也就是输出由之前的信息(也就是状态state)和此时的输入决定。这些连续的信息被保存在循环网络的隐藏状态中，这种隐藏状态管理跨越多个时间步，并一层一层地向前传递，影响网络对每一个新样例的处理。  \n &ensp;&ensp; 循环网络，需要寻找被许多时刻分开的各种事件之间的相关性，这些相关性被称为“长距离依赖”，因为时间下游的事件依赖于之前的一个或多个事件，并且是这些事件的函数。  \n &ensp;&ensp; 因此，可以将RNN理解为是一种跨时间分享权重的方式。\n<a id=\'1.1\'></a>\n<a id=\'1.1\'></a>\n### 1.1 结构图\n![image.png](http://localhost:8081/76d11104-db51-453e-ba74-10edc59f5691.png)\n  &ensp;&ensp; 该图分为左右两图，左边给出的RNN是一个抽象的循环结构，右边是左边RNN展开以后的形式。先来看右边的结构，从下往上依次是序列数据的输入X（图中的绿色结构，可以是时间序列，也可以是文本序列等等）。\n\n![image.png](http://localhost:8081/eccf7c7c-e7c8-4cfc-b5f3-b01ecd11046a.png)\n  &ensp;&ensp; 对于t时刻的x经过一个线性变换$W_{xh}$，然后与t-1时刻经过线性变换$W_{hh}$的$ h_{t-1} $ 相加，再经过一个 非线性（一般使用tanh或者relu函数）以后，形成一个t时刻的中间状态h，然后再经过一个线性变换（W）输出。\n\n<a id=\'1.2\'></a>\n### 1.2 公式\n![image.png](http://localhost:8081/5f5e8cf4-2f4a-4a39-9c87-1054a5a733bd.png)\n展开网络中的![image.png](http://localhost:8081/86875c7c-e0cd-44da-9c95-4bb82aed1d6e.png)参数都是在共享的，就是说不管我们的序列有多长，都是共享这一套参数的。这是RNN很重要的一个特性。\n\n<a id=\'1.3\'></a>\n### 1.3 代码\n为方便对比三者在处理图像数据时的异同，RNN、LSTM、ConvLSTM代码的输入均为二维矢量。数据为随机的矩阵数据。\n```python\n\nimport torch\nfrom torch import nn\nimport numpy as np\n\nnn.RNN(input_size=10, hidden_size=20, num_layers=1)\n\n```\n* PyTorch中RNN类的原型的参数：  \n① 必选参数input_size指定输入序列中单个样本的大小尺寸，比如在NLP中我们可能用用一个10000个长度的向量表示一个单词，则这个input_size就是10000。在咱们的回归案例中，一个序列中包含若干点，而每个点的所代表的函数值（Y）作为一个样本，则咱们案例中的input_size为1。这个参数需要根据自己的实际问题确定。  \n② 必选参数hidden_size指的是隐藏层中输出特征的大小，这个是自定义的超参数。  \n③ 必选参数num_layers指的是纵向的隐藏层的个数，根据实际问题我们一般可以选择1~10层。    \n④ 可选参数batch_first指定是否将batch_size作为输入输出张量的第一个维度，如果是，则输入的尺寸为（batch_size， seq_length，input_size），否则，默认的顺序是（seq_length，batch_size， input_size）。   \n⑤ 可选参数bidirectional指定是否使用双向RNN。\n* RNN的输入包括输入序列和一个初始化的隐藏状态$h_{0}$。输入序列尺寸默认是（sequence_length，batch_size， input_size）。  \n* 隐藏状态$h_{i}$的尺寸是（num_layers， batch_size，hidden_size）。  \n* 输出的尺寸为 （sequence_length， batch_size， hidden_size）  \n每一次RNN运行结果输出中还会附带输出中间隐藏状态$h_{i}$。\n\n下面以一个简单的例子说明怎么在程序中查看他们的尺寸：\n```python\nimport torch\nimport torch.nn as nn\n\nrnn = nn.RNN(10, 20, 2)\ninputs = torch.randn(5, 3, 10)  # (time_step, batch_size, input_size)\nh0 = torch.randn(2, 3, 20)  # (num_layers, batch_size, hidden_size)\noutput, hn = rnn(inputs, h0)\nprint(output.shape)  # (time_step, batch_size, hidden_size)\n\nfor name, param in rnn.named_parameters():\n    if param.requires_grad:\n        print(name, param.size())\n```\n#### 1.3.2 简单的回归案例\n* 训练数据  \n要用到的数据就是这样的一些数据, 我们想要用 sin 的曲线预测出 cos 的曲线\n![image.png](http://localhost:8081/22fd6640-c4c1-44aa-851e-d536e963561a.png)\n', 12, 8);
INSERT INTO `son_chapter` VALUES (48, 1.100, '计算机视觉简介', '计算机视觉简介', 'http://localhost:4040/e6ff4ca6-2ec5-4989-adf9-aaaa8518eab5.mp4', 'http://localhost:4040/f0f2009b-3f70-49fd-abcc-8cd37ba9fea9.pptx', 'Jupyter', NULL, NULL, 14, 7);
INSERT INTO `son_chapter` VALUES (50, 1.200, '计算机视觉应用', '计算机视觉应用领域', 'http://localhost:4040/3313a74b-a188-42c6-a600-3d172c1c3dd9.mp4', '', 'Jupyter', NULL, NULL, 14, 7);
INSERT INTO `son_chapter` VALUES (51, 1.000, '卷积神经网络', '卷积神经网络（Convolutional Neural Networks, CNN）', '', '', 'Jupyter', NULL, '# 卷积神经网络（Convolutional Neural Networks, CNN）\n[1 CNN介绍](#1)\n\n  &ensp; [1.1 卷积层](#1.1)  \n  \n  &ensp; [1.2 池化层](#1.2)  \n  \n  &ensp; [1.3 全连接层](#1.2)\n\n[2 CNN代码实现](#2)\n\n&ensp; [2.1 PyTorch中CNN类的原型](#2.1)\n\n&ensp; [2.2 基于CNN的分类案例$-$手写数字识别](#2.2)\n\n[3 思考与练习](#3)\n<a id=\'1\'></a>\n## 1 CNN介绍\n&ensp;&ensp;近年来，深度学习技术被应用在各个领域，如气象、医学、无人驾驶等。在各种深度学习模型中，卷积神经网络（Convolutional Neural Networks, CNN）是最为广泛使用的网络模型之一，自2012年ImageNet大规模视觉识别竞赛（ILSVRC）取得惊人的结果以来，它一直是计算机视觉任务中的主导方法。\n&ensp;&ensp;CNN是深度学习常用的模型之一，对于具有网格模式的数据（如图片）更加有效，其灵感来自动物视觉皮层的组织，旨在自动且自适应地从低级模式向高级模式学习特征的空间层次。\n&ensp;&ensp;CNN通常由三种类型的层或构件块组成：卷积层、池化层和全连接层。卷积和池化的作用是特征提取，全连接层则是将提取的特征映射为最终的输出，如分类。下面具体阐述CNN的卷积层、池化层和全连接层。\n![Image](https://img-blog.csdnimg.cn/a65e221f59614449bedab8159b0b6f8e.gif)\n\n<a id=\'1.1\'></a>\n### 1.1 卷积层\n&ensp;&ensp;卷积层是CNN体系结构中执行特征提取任务的基本组成部分。特征提取通常包括线性和非线性操作的组合，分别对应卷积操作和激活函数。卷积层有一组称为卷积核或过滤器的特征检测器，适用于整个图像区域。每个特征检测器都有一组可学习权重。这些权重在图像中不会改变，这被称为参数共享，可以减少内存需求并防止发生过拟合。将卷积核与输入做按位相乘并求和后可以获得特征图对应位置处的输出值。重复这个过程，可以获得完整的特征图。卷积核的大小和卷积核的数量是卷积运算的两个关键的超参数。卷积核的大小通常为$3×3$、 $5×5$或$7×7$。卷积核数量没有特殊的值，它只和最后输出的特征图的深度有关，起到决定性作用。卷积过程如下动图所示：\n```python\nclass Conv3x3:\n    # 卷积层使用3*3的filter.\n    def __init__(self, num_filters):\n        self.num_filters = num_filters\n        self.filters = np.random.randn(num_filters, 3, 3) / 9       # 除以9是为了减小初始值的方差\n\n    def iterate_regions(self, image):\n        h, w = image.shape\n\n        for i in range(h - 2):                                   # (h-2)/(w-2)是滤波以单位为1的步长，所需要移动的步数\n            for j in range(w - 2):\n                im_region = image[i:(i + 3), j:(j + 3)]          # （i+3） 3*3的filter所移动的区域\n                yield im_region, i, j\n\n    def forward(self, input):\n        # 28x28\n        self.last_input = input\n\n        h, w = input.shape\n        output = np.zeros((h - 2, w - 2, self.num_filters))      # 创建一个（h-2）*（w-2）的零矩阵用于填充每次滤波后的值\n\n        for im_region, i, j in self.iterate_regions(input):\n            output[i, j] = np.sum(im_region * self.filters, axis=(1, 2))\n\n        return output                                            # 4*4的矩阵经过3*3的filter后得到一个2*2的矩阵\n\n    def backprop(self, d_L_d_out, learn_rate):\n        # d_L_d_out: the loss gradient for this layer\'s outputs\n        # learn_rate: a float\n        d_L_d_filters = np.zeros(self.filters.shape)\n\n        for im_region, i, j in self.iterate_regions(self.last_input):\n            for f in range(self.num_filters):\n                # d_L_d_filters[f]: 3x3 matrix\n                # d_L_d_out[i, j, f]: num\n                # im_region: 3x3 matrix in image\n                d_L_d_filters[f] += d_L_d_out[i, j, f] * im_region\n\n        # Update filters\n        self.filters -= learn_rate * d_L_d_filters\n        return None\n```', 15, 5);
INSERT INTO `son_chapter` VALUES (53, 1.300, 'test', '计算机视觉TEST', '', '', 'Jupyter', NULL, NULL, 14, 7);
INSERT INTO `son_chapter` VALUES (54, 1.200, 'Hello', '', '', '', 'Jupyter', NULL, 'hello world', 12, 8);
INSERT INTO `son_chapter` VALUES (55, 1.300, '舒服', '撒旦法', '', '', 'Jupyter', NULL, NULL, 12, 8);

-- ----------------------------
-- Table structure for son_user_exp
-- ----------------------------
DROP TABLE IF EXISTS `son_user_exp`;
CREATE TABLE `son_user_exp`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `son_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `exp_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `lessonId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of son_user_exp
-- ----------------------------
INSERT INTO `son_user_exp` VALUES (1, 35, 3, 'http://localhost:8888/notebooks/353.ipynb', 8);
INSERT INTO `son_user_exp` VALUES (2, 48, 3, 'http://localhost:8888/notebooks/353.ipynb', 7);
INSERT INTO `son_user_exp` VALUES (3, 50, 3, 'http://localhost:8888/notebooks/363.ipynb', 7);
INSERT INTO `son_user_exp` VALUES (4, 51, 3, 'http://localhost:8888/notebooks/513.ipynb', 5);
INSERT INTO `son_user_exp` VALUES (5, 53, 3, 'http://localhost:8888/notebooks/533.ipynb', 7);
INSERT INTO `son_user_exp` VALUES (6, 52, 3, 'http://localhost:8888/notebooks/523.ipynb', 8);
INSERT INTO `son_user_exp` VALUES (7, 54, 3, 'http://localhost:8888/notebooks/543.ipynb', 8);
INSERT INTO `son_user_exp` VALUES (8, 55, 3, 'http://localhost:8888/notebooks/553.ipynb', 8);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, 'ai');
INSERT INTO `tag` VALUES (2, 'meteo');
INSERT INTO `tag` VALUES (3, 'soft');
INSERT INTO `tag` VALUES (4, 'other');

-- ----------------------------
-- Table structure for tools
-- ----------------------------
DROP TABLE IF EXISTS `tools`;
CREATE TABLE `tools`  (
  `tool_id` int(11) NOT NULL AUTO_INCREMENT,
  `tool_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tool_env` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `upload_time` date NULL DEFAULT NULL,
  `download_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tool_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tools
-- ----------------------------
INSERT INTO `tools` VALUES (1, '《地面气象测报业务系统软件 2004》完整版V3.0.18', 'windows', '2022-05-02', 'http://localhost:4040/《地面气象测报业务系统软件 2004》完整版V3.0.18.exe');
INSERT INTO `tools` VALUES (2, 'navicat', 'windows', '2022-05-30', 'http://localhost:4040/0a7e66b7-b2bf-46dc-84be-eddee0f995f1.zip');
INSERT INTO `tools` VALUES (3, 'FSCapture截图软件', 'windows', '2022-05-30', 'http://localhost:4040/485041af-bd6f-40ac-95d5-6b6c94a3a9e5.rar');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `work_place` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `major` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `qualification` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_time` date NULL DEFAULT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar_image` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clazz_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_id_index`(`user_id`) USING BTREE,
  INDEX `username_index`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'b084fbd93e31cd32436e791be42072df', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-03', '0', 'http://localhost:8081/img.jpg', NULL);
INSERT INTO `users` VALUES (2, 'teacher1', 'b084fbd93e31cd32436e791be42072df', '教师1', '女', NULL, NULL, NULL, NULL, NULL, '17312228383', 'demo1@163.com', '2021-11-03', '1', 'http://localhost:8081/img.jpg', NULL);
INSERT INTO `users` VALUES (3, 'student1', 'b084fbd93e31cd32436e791be42072df', '学生1', '男', '2003-02-06', '北京/气象谷', '打杂滴', '计算机科学', '硕士', '17312222222', 'demo1@163.com', '2021-11-04', '2', 'http://localhost:8081/28812c7c-2604-4957-9cd0-f8ee4eaf4fbc.jpg', NULL);
INSERT INTO `users` VALUES (4, 'teacher2', 'b084fbd93e31cd32436e791be42072df', '教师2', '男', '2000-06-29', '北京/气象谷', '打杂滴', '计算机科学', '硕士', '17312260306', 'xueleijia@163.com', '2022-02-22', '1', 'http://localhost:8081/img.jpg', NULL);
INSERT INTO `users` VALUES (5, 'teacher3', 'b084fbd93e31cd32436e791be42072df', '教师3', '男', NULL, NULL, NULL, NULL, NULL, '17327190317', 'aaa@123.com', '2022-04-03', '1', 'http://localhost:8081/img.jpg', NULL);
INSERT INTO `users` VALUES (6, 'student2', 'b084fbd93e31cd32436e791be42072df', '李四', '男', '1989-06-05', '北京/气象谷', '打杂滴', '计算机科学', '硕士', '17312260409', 'asaa@sina.com', '2022-04-04', '2', 'http://localhost:8081/img.jpg', NULL);
INSERT INTO `users` VALUES (49, 'xiaohong', '4bc346a259ed79cf8c6f223ac39bdf22', '小红', '女', '2023-05-05', '北京/气象谷', '打杂滴', '计算机科学', '硕士', '18865437393', NULL, '2023-05-05', '2', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
