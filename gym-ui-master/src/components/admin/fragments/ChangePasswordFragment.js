import React from 'react';
import {
    Box,
    Breadcrumbs,
    Button,
    colors,
    Container,
    CssBaseline,
    Link,
    Paper,
    TextField,
    Typography,
    withStyles
} from "@material-ui/core";
import RouterLink from "../../common/RouterLink";
import axiosInstance from "../axiosInstance";

const styles = theme => ({
    '@global': {
        body: {
            backgroundColor: theme.palette.common.white,
        },
    },
    breadcrumbPaper: {
        margin: theme.spacing(2, 0, 2),
        padding: theme.spacing(2),
    },
    paper: {
        padding: theme.spacing(2, 3),
        display: 'flex',
        flexDirection: 'column',
    },
    title: {
        margin: theme.spacing(2, 0),
    },
    field: {
        margin: theme.spacing(1, 0),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
    errorsPaper: {
        backgroundColor: theme.palette.error.main,
        color: theme.palette.common.white,
        marginBottom: theme.spacing(2),
        padding: theme.spacing(1),
    },
    successMessagePaper: {
        backgroundColor: colors.green["600"],
        color: theme.palette.common.white,
        marginBottom: theme.spacing(2),
        padding: theme.spacing(1),
    }
});

class ChangePasswordPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {currentPassword: '', newPassword: '', repeatPassword: '', error: null, successMessage: null};
    }

    componentDidMount() {
    }

    render() {
        const {classes, history} = this.props;
        const self = this;

        const handleSubmit = function () {
            if (self.state.newPassword !== self.state.repeatPassword) {
                self.setState({error: "两次输入的新密码不一致。"});
            } else {
                axiosInstance.post("/admin/updatePassword", {
                    currentPassword: self.state.currentPassword,
                    newPassword: self.state.newPassword
                }).then((res) => {
                    self.setState({successMessage: "修改成功。您需要重新登录。"});
                    setTimeout(function () {
                        history.push("/adminSignIn");
                    }, 3000);
                    sessionStorage.removeItem("adminToken");
                }).catch((reason) => {
                    self.setState({error: reason.response.data.message});
                });
            }
        };

        return (
            <div>
                <Paper className={classes.breadcrumbPaper}>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" component={RouterLink} to="/admin/">首页</Link>
                        <Typography color="textPrimary">修改密码</Typography>
                    </Breadcrumbs>
                </Paper>
                <Container component="main" maxWidth="xs">
                    <CssBaseline/>
                    <Paper className={classes.paper}>
                        <Typography component="h1" variant="h5" className={classes.title}>
                            修改密码
                        </Typography>
                        {this.state.successMessage !== null ? (
                            <Paper className={classes.successMessagePaper}>
                                <Typography>{this.state.successMessage}</Typography>
                            </Paper>
                        ) : null}
                        {this.state.error !== null ? (
                            <Paper className={classes.errorsPaper}>
                                <Typography>{this.state.error}</Typography>
                            </Paper>
                        ) : null}
                        <Box>
                            <TextField className={classes.field} variant="outlined" required fullWidth
                                       label="旧密码" type="password" autoComplete="off"
                                       value={this.state.currentPassword}
                                       onChange={(event) => {
                                           this.setState({
                                               currentPassword: event.target.value,
                                               error:null
                                           })
                                       }}/>
                        </Box>
                        <Box>
                            <TextField className={classes.field} variant="outlined" required fullWidth
                                       label="新密码" type="password" autoComplete="off"
                                       value={this.state.newPassword}
                                       onChange={(event) => {
                                           this.setState({
                                               newPassword: event.target.value,
                                               error:null
                                           })
                                       }}/>
                        </Box>
                        <Box>
                            <TextField className={classes.field} variant="outlined" required fullWidth
                                       label="确认密码" type="password" autoComplete="off"
                                       value={this.state.repeatPassword}
                                       onChange={(event) => {
                                           this.setState({
                                               repeatPassword: event.target.value,
                                               error:null
                                           })
                                       }}/>
                        </Box>
                        <Button type="submit" fullWidth variant="contained" color="primary"
                                className={classes.submit} onClick={handleSubmit}>
                            确认
                        </Button>
                    </Paper>
                </Container>
            </div>
        );

    }
}

export default withStyles(styles)(ChangePasswordPage);