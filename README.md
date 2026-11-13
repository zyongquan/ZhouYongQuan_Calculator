# ZhouYongQuan_Calculator

四川师范大学 计算机科学学院 · Android 开发上机测试作业
两个 Activity 组成的简易计算器 App（Kotlin + XML 布局 + Material 3）。

## 学生信息

| 项目 | 内容 |
| --- | --- |
| 姓名 | 周勇权 |
| 学号 | 2024180750 |
| 班级 | 计科 1 班 |
| 字体颜色 | 学号尾号 0 → 红色（界面文字统一使用红色系） |

## 功能说明

1. **Activity1（计算器主界面）**
   - 输入两个数（支持小数与负数）；
   - 选择运算符 `+ - × ÷`；
   - 调用计算类执行运算并显示结果；
   - 横竖屏均可正常显示结果，旋转屏幕后输入与结果不丢失；
   - 标题栏显示“周勇权 2024180750”，右上角“历史”菜单可打开 Activity2。
2. **Activity2（历史记录）**
   - 使用 `RecyclerView` 展示曾经执行过的运算，最新的排在最前面；
   - 每条记录显示算式与时间，例如 `12.5 ÷ 4 = 3.125`；
   - 无记录时显示空状态提示；
   - 历史记录通过 `SharedPreferences` 持久化，重启应用后仍然保留。

## 代码结构

```
app/src/main/java/com/example/zhouyongquan_calculator/
├── Calculator.kt        # 计算类：加、减、乘、除（除数为 0 抛异常）
├── NumberFormatter.kt   # 结果格式化（去掉多余小数 0）
├── HistoryRecord.kt     # 一条历史记录的数据模型
├── HistoryStore.kt      # 历史记录仓库（内存缓存 + SharedPreferences）
├── HistoryAdapter.kt    # RecyclerView 适配器
├── MainActivity.kt      # Activity1：输入、计算、菜单跳转
└── HistoryActivity.kt   # Activity2：历史记录列表
```

布局：`activity_main.xml`（竖屏）、`layout-land/activity_main.xml`（横屏左右分栏）、
`activity_history.xml`、`item_history.xml`。

## 构建与运行

```bash
./gradlew :app:assembleDebug     # 构建 Debug APK
./gradlew :app:testDebugUnitTest # 运行 Calculator 单元测试
```

APK 输出位置：`app/build/outputs/apk/debug/app-debug.apk`。

## 提交记录

- Activity1：计算类、主界面、横竖屏适配与历史菜单入口；
- Activity2：RecyclerView 历史记录页与数据持久化。
