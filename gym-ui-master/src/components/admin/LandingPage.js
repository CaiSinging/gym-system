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
                                客户管理
                            </Typography>
                            <Typography variant="body2" component="p">
                                管理员可以在本模块中对客户进行管理。
                                在“查看客户”中可以看到已注册的客户信息，
                                也可以进一步对客户进行一些权限的操作。
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button component={RouterLink} to="/admin/vip">查看客户</Button>
                        </CardActions>
                    </Card>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Card className={classes.card}>
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                教练管理
                            </Typography>
                            <Typography variant="body2" component="p">
                                管理员可以在“查看教练”中查看在职教练的详情信息，
                                在“新增教练”中添加新教练，
                                在“查看假条”中查看教练的请假申请并可以进行批复。
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button component={RouterLink} to="/admin/teacher">查看教练</Button>
                            <Button component={RouterLink} to="/admin/addTeacher">新增教练</Button>
                            <Button component={RouterLink} to="/admin/leave">查看假条</Button>
                        </CardActions>
                    </Card>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Card className={classes.card}>
                        <CardContent>
                            <Typography variant="h5" component="h2">
                                课程管理
                            </Typography>
                            <Typography variant="body2" component="p">
                                管理员可以对健身房的训练课程进行管理。
                                可以在“查看课程”中可以了解现有课程的详情，
                                在“新增课程”中完善信息后可以添加新课程。
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button component={RouterLink} to="/admin/course">查看课程</Button>
                            <Button component={RouterLink} to="/admin/addCourse">新增课程</Button>
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
                            <Button component={RouterLink} to="/admin/myInfo">个人信息</Button>
                            <Button component={RouterLink} to="/admin/changePassword">修改密码</Button>
                        </CardActions>
                    </Card>
                </Grid>
            </Grid>
        </div>
    );
}