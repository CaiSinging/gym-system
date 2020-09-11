import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Paper from '@material-ui/core/Paper';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import {makeStyles} from '@material-ui/core/styles';
import Configuration from "../../config";
import axios from "axios";
import Copyright from "../common/Copyright";
import RouterLink from "../common/RouterLink";

const useStyles = makeStyles(theme => ({
    root: {
        height: '100vh',
    },
    image: {
        backgroundImage: 'url(https://images.unsplash.com/photo-1534438327276-14e5300c3a48?auto=format&fit=crop&w=1280&q=60)',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
    },
    paper: {
        margin: theme.spacing(8, 4),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
    errorsPaper: {
        backgroundColor: theme.palette.error.main,
        color: theme.palette.common.white,
        margin: theme.spacing(3, 0, 2),
        padding: theme.spacing(1),
        width: '100%',
        textAlign: 'left',
    },
}));

export default function SignInPage(props, context) {
    const classes = useStyles();
    const [email, setEmail] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [errorMessage, setErrorMessage] = React.useState(null);

    let history = props.history;

    const handleLogin = () => {
        axios.post(Configuration.API_ENDPOINT + '/auth/guest_sign_in', {
            email: email,
            password: password
        }).then((resp) => {
            sessionStorage.setItem("customerToken", resp.data.accessToken);
            console.log(props, context);
            history.push("/customer/");
        }).catch((error)=>{
            if (error.response) {
                if (error.response.data.message === 'USER_NOT_EXISTS_OR_PASSWORD_INCORRECT')
                    setErrorMessage('用户不存在或密码错误！');
                else if (error.response.data.message === 'USER_NOT_VIP')
                    setErrorMessage('您还不是VIP，不能登录。');
            }
        });
    };

    return (
        <Grid container component="main" className={classes.root}>
            <CssBaseline/>
            <Grid item xs={0} sm={4} md={7} className={classes.image}
                  style={{display: "flex", alignItems: "center"}}>
            </Grid>
            <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        顾客登录
                    </Typography>
                    {errorMessage !== null ? (
                        <Paper className={classes.errorsPaper}>
                            <Typography>{errorMessage}</Typography>
                        </Paper>
                    ) : null}
                    <TextField variant="outlined" margin="normal" required fullWidth
                               id="email" name="email" label="电子邮件地址" autoComplete="off"
                               value={email} onChange={(e) => {
                        setEmail(e.target.value)
                    }}/>
                    <TextField variant="outlined" margin="normal" required fullWidth
                               id="password" name="password" type="password" label="密码" autoComplete="off"
                               value={password} onChange={(e) => {
                        setPassword(e.target.value)
                    }}/>
                    <Button type="submit" fullWidth variant="contained"
                            color="primary" className={classes.submit} onClick={handleLogin}>
                        登录
                    </Button>
                    <Grid container>
                        <Grid item xs>
                            <Link component={RouterLink} to="/customerSignUp" variant="body2">
                                注册一个账户
                            </Link><br/>
                            <Link component={RouterLink} to="/customerResetPwd" variant="body2">
                                找回密码
                            </Link>
                        </Grid>
                        <Grid item style={{textAlign: "right"}}>
                            <Link component={RouterLink} to="/teacherSignIn" variant="body2">
                                教练登录
                            </Link>
                            <br/>
                            <Link component={RouterLink} to="/adminSignIn" variant="body2">
                                管理人员登录
                            </Link>
                        </Grid>
                    </Grid>
                    <Box mt={5}>
                        <Copyright/>
                    </Box>
                </div>
            </Grid>
        </Grid>
    );

}
