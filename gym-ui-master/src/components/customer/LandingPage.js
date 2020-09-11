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
                                课程选退
                            </Typography>
                            <Typography variant="body2" component="p">
                                客户可以在本模块进行训练课程的选课和退课。
                                可以在“进行选课”选择课程，
                                在“我的课表”查看或退选已选的课程。
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button component={RouterLink} to="/customer/electCourse">进行选课</Button>
                            <Button component={RouterLink} to="/customer/myCourses">我的课表</Button>
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
                                客户可以在本模块体验体质测试相关的功能。
                                可以在“预约体测”预约体测项目，
                                在“预约记录”里查看已预约的体测详情，
                                在“测试报告”中查看体测结果。
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button component={RouterLink} to="/customer/orderTest">预约体测</Button>
                            <Button component={RouterLink} to="/customer/myTest">预约记录</Button>
                            <Button component={RouterLink} to="/customer/myReport">测试报告</Button>
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
                            <Button component={RouterLink} to="/customer/myInfo">个人信息</Button>
                            <Button component={RouterLink} to="/customer/changePassword">修改密码</Button>
                        </CardActions>
                    </Card>
                </Grid>
            </Grid>
        </div>
    );
}