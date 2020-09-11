import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Grid from "@material-ui/core/Grid";
import RouterLink from "../common/RouterLink";


const useStyles = makeStyles((theme) => ({
    container: {
        marginTop: theme.spacing(3),
    },
    card: {
        minWidth: 275,
    },
    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    },
}));

export default function LandingPage() {
    const classes = useStyles();

    return (
        <div className={classes.container}>
            <Grid container spacing={2}>
                <Grid item xs={12} md={4}>
                    <Card className={classes.card}>
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                请假申请
                            </Typography>
                            <Typography variant="body2" component="p">
                                教练如果因故不能按时上班，需要提前申请。
                                可以在“进行请假”提出请假申请，申请后可以在“我的假条”查看假条详情及管理员的批准情况。
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button component={RouterLink} to="/teacher/applyLeave">进行请假</Button>
                            <Button component={RouterLink} to="/teacher/myLeave">我的假条</Button>
                        </CardActions>
                    </Card>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Card className={classes.card}>
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                体质测试
                            </Typography>
                            <Typography variant="body2" component="p">
                                教练可以在这个模块查看在体质测试模块中预约自己为带测教练的客户名单。
                                客户选择了体测项目会更新到相应的教练的体质测试模块中。
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button component={RouterLink} to="/teacher/testVip">预约名单</Button>
                        </CardActions>
                    </Card>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Card className={classes.card}>
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                我的课程
                            </Typography>
                            <Typography variant="body2" component="p">
                                教练可以在这个模块查看在训练课程中预约了自己课程的客户名单。
                                客户选择了训练课程会更新到相应教练的课程模块中。
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button component={RouterLink} to="/teacher/myCourse">查看课表</Button>
                        </CardActions>
                    </Card>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Card className={classes.card}>
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                个人中心
                            </Typography>
                            <Typography variant="body2" component="p">
                                本模块主要可以查看和编辑个人信息。
                                可以在“个人信息”查看详情并进行修改，
                                也可以在“修改密码”专门进行对密码的修改。
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button component={RouterLink} to="/teacher/myInfo">个人信息</Button>
                            <Button component={RouterLink} to="/teacher/changePassword">修改密码</Button>
                        </CardActions>
                    </Card>
                </Grid>
            </Grid>
        </div>
    );
}