import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TablePagination from "@material-ui/core/TablePagination";
import React from "react";
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core";
import TableHead from "@material-ui/core/TableHead";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Link from "@material-ui/core/Link";
import RouterLink from "../../common/RouterLink";
import PropTypes from "prop-types";
import Moment from "react-moment";
import axiosInstance from "../axiosInstance";

const styles = (theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing(3),
    },
    breadcrumbPaper: {
        marginBottom: theme.spacing(2),
        padding: theme.spacing(2),
    },
    paper: {
        width: '100%',
        marginBottom: theme.spacing(2),
    },
    title: {
        padding: theme.spacing(2),
    },
    table: {
        minWidth: 750,
    },
    tableWrapper: {
        overflowX: 'auto',
    },
}));

const headCells = [
    {id: 'time', align: 'left', label: '报告日期'},
    {id: 'teacher', align: 'right', label: '教练'},
    {id: 'height', align: 'right', label: '身高 (cm)'},
    {id: 'weight', align: 'right', label: '体重 (kg)'},
    {id: 'fat', align: 'right', label: '体脂 (%)'},
    {id: 'vitalCapacity', align: 'right', label: '肺活量'},
    {id: 'sitAndReach', align: 'right', label: '坐位体前屈 (cm)'},
    {id: 'sitUp', align: 'right', label: '仰卧起坐 (个)'},
    {id: 'gripPower', align: 'right', label: '握力 (N)'},
];


class MyTestReportFragment extends React.Component {

    constructor(props) {
        super(props);
        this.state = {physicalTests: [], page: 0, rowsPerPage: 5};
    }

    componentDidMount() {
        axiosInstance.get('/physicalTests/myTestReports').then(res => {
            this.setState({physicalTests: res.data.physicalTests});
        });
    }


    render() {
        const {classes} = this.props;

        return (<div className={classes.root}>
            <Paper className={classes.breadcrumbPaper}>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" component={RouterLink} to="/customer/">首页</Link>
                    <Typography color="textPrimary">测试报告</Typography>
                </Breadcrumbs>
            </Paper>
            <Paper className={classes.paper}>
                <Typography className={classes.title} variant="h6" id="tableTitle">
                    测试报告
                </Typography>
                <div className={classes.tableWrapper}>
                    <Table
                        className={classes.table}
                        aria-labelledby="tableTitle"
                        aria-label="enhanced table"
                    >
                        <TableHead>
                            <TableRow>
                                {headCells.map(headCell => (
                                    <TableCell
                                        key={headCell.id}
                                        align={headCell.align}
                                    >
                                        {headCell.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.physicalTests.slice(
                                this.state.page * this.state.rowsPerPage,
                                (this.state.page + 1) * this.state.rowsPerPage)
                                .map((row, index) => {
                                    return (
                                        <TableRow hover tabIndex={-1} key={row.name}>
                                            {/*onClick={event => handleRowClick(event, row.name)}*/}
                                            <TableCell component="th" scope="row">
                                                <Moment format="YYYY-MM-DD HH:mm" unix>{row.time / 1000}</Moment>
                                            </TableCell>
                                            <TableCell align="right">{row.teacher.name}</TableCell>
                                            <TableCell align="right">{row.height}</TableCell>
                                            <TableCell align="right">{row.weight}</TableCell>
                                            <TableCell align="right">{row.fat}</TableCell>
                                            <TableCell align="right">{row.vitalCapacity}</TableCell>
                                            <TableCell align="right">{row.sitAndReach}</TableCell>
                                            <TableCell align="right">{row.sitUp}</TableCell>
                                            <TableCell align="right">{row.gripPower}</TableCell>
                                        </TableRow>
                                    );
                                })}

                        </TableBody>
                    </Table>
                </div>
                <TablePagination component="div" page={this.state.page} count={this.state.physicalTests.length}
                                 rowsPerPage={this.state.rowsPerPage} rowsPerPageOptions={[5, 10, 25, 50]}
                                 onChangePage={(event, newPage) => this.setState({page: newPage})}
                                 onChangeRowsPerPage={(event) => {
                                     this.setState({rowsPerPage: parseInt(event.target.value), page: 0});
                                 }}/>
            </Paper>
        </div>)
    }
}

MyTestReportFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(MyTestReportFragment);