const express = require("express");
const bodyParser = require("body-parser");
const mysql = require("mysql");
const session = require("express-session");
const crypto = require("crypto");
const nodemailer = require("nodemailer");
const jwt = require("jsonwebtoken");
const path = require("path");
const cors = require("cors");

const app = express();

// Generate a random secret key
const secretKey = crypto.randomBytes(32).toString("hex");

// Serve static files from the 'upload' directory
app.use("/upload", express.static(path.join(__dirname, "upload")));

app.use(cors());

// Configure session middleware
app.use(
	session({
		secret: secretKey,
		resave: false,
		saveUninitialized: false,
		cookie: { secure: false }, // Set to true if using HTTPS
	})
);

app.use(bodyParser.json());

const conn = mysql.createConnection({
	host: "localhost",
	user: "root",
	password: "",
	database: "absensi",
});

conn.connect(function (err) {
	if (err) throw err;
	console.log("connected to database ....");
});

//GET NIP PEGAWAI
app.get("/get_nip_pegawai", function (req, res) {
	let sql = "SELECT nip FROM data_akun WHERE access_level = 'pegawai'";
	conn.query(sql, function (err, result) {
		if (err) {
			console.error("Error fetching NIP pegawai:", err);
			return res.status(500).json({
				message: "Internal Server Error",
				status: 500,
				error: err.message,
				response: null,
			});
		}

		// Extracting nip values into a single array
		let nipArray = result.map((item) => item.nip);

		res.json({
			message: "NIP Pegawai",
			status: 200,
			error: null,
			response: nipArray,
		});
	});
});

//GET NIP PETUGAS

app.get("/get_nip_petugas", function (req, res) {
	let sql = "SELECT nip FROM data_akun WHERE access_level = 'petugas' ";
	let query = conn.query(sql, function (err, result) {
		if (err) {
			console.error("Error fetching NIP petugas:", err);
			return res.status(500).json({
				message: "Internal Server Error",
				status: 500,
				error: err.message,
				response: null,
			});
		}

		let nipArray = result.map((item) => item.nip);

		res.json({
			message: "NIP PETUGAS",
			status: 200,
			error: null,
			response: nipArray,
		});
	});
});

//SIGN UP PEGAWAI

app.post("/signup_pegawai", function (req, res) {
	let nip = req.body.nip;
	let checkIfExistsQuery = "SELECT * FROM data_akun WHERE nip = ?";
	let checkIfExistsParams = [nip];
	conn.query(checkIfExistsQuery, checkIfExistsParams, function (err, rows) {
		if (err) {
			console.error("Error checking NIP existence:", err);
			res.status(500).json({
				status: 500,
				error: "Internal Server Error",
				response: null,
			});
		} else {
			if (rows.length > 0) {
				// NIP already exists, return an error
				res.status(400).json({
					message: "NIP already exists",
					status: 400,
					error: "Bad Request",
					response: null,
				});
			} else {
				// NIP does not exist, proceed with user registration
				let data = {
					username: req.body.username,
					password: req.body.password,
					nip: nip,
					email: req.body.email,
					phone: req.body.phone,
				};
				let sql = "INSERT INTO data_akun SET ?, access_level = 'pegawai'";
				conn.query(sql, data, function (err, result) {
					if (err) {
						console.error("Error registering user:", err);
						res.status(500).json({
							status: 500,
							error: "Internal Server Error",
							response: null,
						});
					} else {
						console.log("User registered successfully");
						res.status(200).json({
							message: "User registered successfully",
							status: 200,
							error: null,
							response: result,
						});
					}
				});
			}
		}
	});
});

//SIGN UP PETUGAS

app.post("/signup_petugas", function (req, res) {
	let nip = req.body.nip;
	let checkIfExistsQuery = "SELECT * FROM data_akun WHERE nip = ?";
	let checkIfExistsParams = [nip];
	conn.query(checkIfExistsQuery, checkIfExistsParams, function (err, rows) {
		if (err) {
			console.error("Error checking NIP existence:", err);
			res.status(500).json({
				status: 500,
				error: "Internal Server Error",
				response: null,
			});
		} else {
			if (rows.length > 0) {
				// NIP already exists, return an error
				res.status(400).json({
					message: "NIP already exists",
					status: 400,
					error: "Bad Request",
					response: null,
				});
			} else {
				// NIP does not exist, proceed with user registration
				let data = {
					username: req.body.username,
					password: req.body.password,
					nip: req.body.nip,
					email: req.body.email,
				};
				let sql = "INSERT INTO data_akun SET ?, access_level = 'petugas'";
				conn.query(sql, data, function (err, result) {
					if (err) {
						console.error("Error registering user:", err);
						res.status(500).json({
							status: 500,
							error: "Internal Server Error",
							response: null,
						});
					} else {
						console.log("User registered successfully");
						res.status(200).json({
							message: "User registered successfully",
							status: 200,
							error: null,
							response: result,
						});
					}
				});
			}
		}
	});
});

//SIGN UP MANAGER

app.post("/signup_manager", function (req, res) {
	let nip = req.body.nip;
	let checkIfExistsQuery = "SELECT * FROM data_akun WHERE nip = ?";
	let checkIfExistsParams = [nip];
	conn.query(checkIfExistsQuery, checkIfExistsParams, function (err, rows) {
		if (err) {
			console.error("Error checking NIP existence:", err);
			res.status(500).json({
				status: 500,
				error: "Internal Server Error",
				response: null,
			});
		} else {
			if (rows.length > 0) {
				// NIP already exists, return an error
				res.status(400).json({
					message: "NIP already exists",
					status: 400,
					error: "Bad Request",
					response: null,
				});
			} else {
				// NIP does not exist, proceed with user registration
				let data = {
					username: req.body.username,
					password: req.body.password,
					nip: req.body.nip,
					email: req.body.email,
				};
				let sql = "INSERT INTO data_akun SET ?, access_level = 'manager'";
				conn.query(sql, data, function (err, result) {
					if (err) {
						console.error("Error registering user:", err);
						res.status(500).json({
							status: 500,
							error: "Internal Server Error",
							response: null,
						});
					} else {
						console.log("User registered successfully");
						res.status(200).json({
							message: "User registered successfully",
							status: 200,
							error: null,
							response: result,
						});
					}
				});
			}
		}
	});
});

//LOGIN

app.post("/login", function (req, res) {
	let username = req.body.username;
	let password = req.body.password;

	// Check if the user is already logged in with an active session
	if (req.session && req.session.user) {
		// User is already logged in, redirect to their dashboard based on access_level
		const user = req.session.user;
		return redirectToDashboard(user.access_level, res);
	}

	// Proceed with the login process if not already logged in
	if (!username || !password) {
		console.error("No username or password provided");
		return res.status(400).json({
			message: "No username or password provided",
			status: 400,
			error: "Bad Request",
			response: null,
		});
	}

	let checkUserQuery = "SELECT * FROM data_akun WHERE username = ?";
	conn.query(checkUserQuery, [username], function (err, result) {
		if (err) {
			console.error("Error checking username:", err);
			return res.status(500).json({
				status: 500,
				error: "Internal Server Error",
				response: null,
			});
		}

		if (result.length === 0) {
			console.log("Username not found");
			return res.status(404).json({
				message: "Username not found",
				status: 404,
				error: "Not Found",
				response: null,
			});
		}

		// Username exists, check password
		const user = result[0];
		if (user.password !== password) {
			console.log("Incorrect password");
			return res.status(401).json({
				message: "Incorrect password",
				status: 401,
				error: "Unauthorized",
				response: null,
			});
		}

		// Password is correct, set session based on access_level
		req.session.user = user; // Store user data in session

		// Redirect to the appropriate dashboard based on access_level
		return redirectToDashboard(user.access_level, res);
	});
});

// Function to redirect to the appropriate dashboard based on access_level
function redirectToDashboard(accessLevel, res) {
	let redirectUrl;
	switch (accessLevel) {
		case "pegawai":
			redirectUrl = "pegawai-dashboard";
			break;
		case "petugas":
			redirectUrl = "petugas-dashboard";
			break;
		case "manager":
			redirectUrl = "manager-dashboard";
			break;
		default:
			console.log("Invalid access level");
			return res.status(403).json({
				message: "Invalid access level",
				status: 403,
				error: "Forbidden",
				response: null,
			});
	}

	return res.status(200).json({
		message: `Login success as ${accessLevel}`,
		redirectUrl: redirectUrl,
		status: 200,
		error: null,
		response: null,
	});
}

//FORGOT PASSWORD (SEND LINK)
const transporter = nodemailer.createTransport({
	service: "gmail",
	auth: {
		user: "c11210020@john.petra.ac.id",
		pass: "hzov ylbr jkdl ecej",
	},
	tls: {
		rejectUnauthorized: false,
	},
});

// Define route to handle sending reset password email
app.post("/send_reset_password", (req, res) => {
	// Generate a unique token for the reset link
	const token = crypto.randomBytes(20).toString("hex");

	// Set expiration time (1 hour from now)
	const expiresAt = new Date(Date.now() + 3600000); // 1 hour in milliseconds

	// Save token and its expiration time into the database
	const insertQuery =
		"INSERT INTO reset_tokens (email, token, expires_at) VALUES (?, ?, ?)";
	const insertParams = [req.body.email, token, expiresAt];
	conn.query(insertQuery, insertParams, (err, result) => {
		if (err) {
			console.error("Error inserting reset token into database:", err);
			return res.status(500).json({
				status: 500,
				error: "Internal Server Error",
				response: null,
			});
		}

		// Construct the reset password link
		const resetLink = `http://localhost:8000/reset-password?token=${token}`;

		const mailOptions = {
			from: "<no-reply>",
			to: req.body.email,
			subject: "Reset Your Password",
			html: `Hello,<br><br>You have requested to reset your password. Please click the link below to reset your password:<br><br><a href="${resetLink}">Reset Password</a>`,
		};

		transporter.sendMail(mailOptions, function (error, info) {
			if (error) {
				console.log(error);
				res.status(500).send("Error sending email");
			} else {
				console.log("Email sent: " + info.response);
				res.status(200).send("Reset password email sent successfully");
			}
		});
	});
});

//RESET PASSWORD (UPDATE PASSWORD)

app.post("/reset_password/:token", (req, res) => {
	const { token } = req.params;
	const { new_password } = req.body;

	// Check if the token exists in the database and is not expired
	const checkTokenQuery =
		"SELECT * FROM reset_tokens WHERE token = ? AND expires_at > NOW()";
	conn.query(checkTokenQuery, [token], (err, result) => {
		if (err) {
			console.error("Error checking reset token:", err);
			return res.status(500).json({
				status: 500,
				error: "Internal Server Error",
				response: null,
			});
		}

		if (result.length === 0) {
			// Token not found or expired
			return res.status(400).json({
				status: 400,
				error: "Invalid or expired token",
				response: null,
			});
		}

		// Token is valid, update the user's password
		const email = result[0].email;
		const updatePasswordQuery =
			"UPDATE data_akun SET password = ? WHERE email = ?";
		conn.query(updatePasswordQuery, [new_password, email], (err, result) => {
			if (err) {
				console.error("Error updating password:", err);
				return res.status(500).json({
					status: 500,
					error: "Internal Server Error",
					response: null,
				});
			}

			// Password updated successfully, delete the reset token from the database
			const deleteTokenQuery = "DELETE FROM reset_tokens WHERE token = ?";
			conn.query(deleteTokenQuery, [token], (err, result) => {
				if (err) {
					console.error("Error deleting reset token:", err);
					return res.status(500).json({
						status: 500,
						error: "Internal Server Error",
						response: null,
					});
				}

				return res.status(200).json({
					status: 200,
					message: "Password reset successful",
					response: [email, new_password, result],
				});
			});
		});
	});
});

//ENTRY KEHADIRAN
const officeLatitude = -7.275621229313711;
const officeLongitude = 112.78065128645274;
const maxDistance = 20; // Maximum distance allowed from the office

app.post("/entry_kehadiran", function (req, res) {
	// Extract latitude and longitude from the request
	const { latitude, longitude } = req.body;

	// Calculate the distance between the office and the employee's location
	const distance = calculateDistance(
		officeLatitude,
		officeLongitude,
		latitude,
		longitude
	);

	// Check if the distance exceeds the maximum allowed distance
	if (distance > maxDistance) {
		return res.status(400).json({
			message: "You are not in the office area",
			status: 400,
			error: "Location check failed",
			response: null,
		});
	}

	// If the location is within the office area, proceed with inserting the attendance
	const currentDate = new Date();
	const jakartaOffset = 7 * 60 * 60 * 1000;
	const jakartaTime = new Date(currentDate.getTime() + jakartaOffset);
	const waktu_masuk = jakartaTime.toISOString();
	let sql = "INSERT INTO log_absensi SET ?";
	let data = {
		nip_pegawai: req.session.user.nip,
		waktu_masuk: waktu_masuk,
		foto_bukti_masuk: req.body.foto_bukti_masuk,
	};
	conn.query(sql, data, function (err, result) {
		if (err) {
			console.error("Error entering kehadiran:", err);
			return res.status(500).json({
				message: "Internal Server Error",
				status: 500,
				error: err.message,
				response: null,
			});
		}
		res.json({
			message: "Kehadiran entered",
			status: 200,
			error: null,
			response: result,
		});
	});
});

// Function to calculate the distance between two sets of coordinates using the Haversine formula
function calculateDistance(lat1, lon1, lat2, lon2) {
	const earthRadius = 6371000; // Earth radius in meters
	const dLat = toRadians(lat2 - lat1);
	const dLon = toRadians(lon2 - lon1);
	const a =
		Math.sin(dLat / 2) * Math.sin(dLat / 2) +
		Math.cos(toRadians(lat1)) *
			Math.cos(toRadians(lat2)) *
			Math.sin(dLon / 2) *
			Math.sin(dLon / 2);
	const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	const distance = earthRadius * c;
	return distance;
}

// Function to convert degrees to radians
function toRadians(degrees) {
	return (degrees * Math.PI) / 180;
}

//ENTRY PULANG
app.post("/entry_pulang", function (req, res) {
	// Extract latitude, longitude, and foto_bukti_pulang from the request
	const { latitude, longitude, foto_bukti_pulang } = req.body;

	// Calculate the distance between the office and the employee's location
	const distance = calculateDistance(
		officeLatitude,
		officeLongitude,
		latitude,
		longitude
	);

	// Check if the distance exceeds the maximum allowed distance
	if (distance > maxDistance) {
		return res.status(400).json({
			message: "You are not in the office area",
			status: 400,
			error: "Location check failed",
			response: null,
		});
	}

	// If the location is within the office area, proceed with inserting the attendance
	const currentDate = new Date();
	const jakartaOffset = 7 * 60 * 60 * 1000;
	const jakartaTime = new Date(currentDate.getTime() + jakartaOffset);
	const waktu_pulang = jakartaTime.toISOString();
	let sql =
		"UPDATE log_absensi SET waktu_pulang = ?, foto_bukti_pulang = ? WHERE nip_pegawai = ? AND waktu_pulang IS NULL";
	let query = conn.query(
		sql,
		[waktu_pulang, foto_bukti_pulang, req.session.user.nip],
		function (err, result) {
			if (err) {
				console.error("Error entering pulang:", err);
				return res.status(500).json({
					message: "Internal Server Error",
					status: 500,
					error: err.message,
					response: null,
				});
			}
			res.json({
				message: "Pulang entered",
				status: 200,
				error: null,
				response: result,
			});
		}
	);
});

//SHOW LIST ABSENSI

app.get("/list_absensi", (req, res) => {
	const sql =
		"SELECT log_absensi.*, pembagian_tugas.* FROM log_absensi INNER JOIN pembagian_tugas ON log_absensi.nip_pegawai = pembagian_tugas.nip_pegawai WHERE pembagian_tugas.nip_petugas = ? ORDER BY log_absensi.id DESC; ";
	conn.query(sql, [req.session.user.nip], (err, result) => {
		if (err) {
			console.error("Error executing MySQL query: " + err.stack);
			res.status(500).json({ error: "Error fetching data from database" });
			return;
		}
		res.json(result);
	});
});

// app.get("/list_absensi", function (req, res) {
// 	let sql =
// 		"SELECT log_absensi.*, pembagian_tugas.* FROM log_absensi INNER JOIN pembagian_tugas ON log_absensi.nip_pegawai = pembagian_tugas.nip_pegawai WHERE pembagian_tugas.nip_petugas = ? ORDER BY log_absensi.id DESC; ";
// 	let query = conn.query(sql, ["00022"], function (err, result) {
// 		if (err) {
// 			console.error("Error fetching list absensi:", err);
// 			return res.status(500).json({
// 				message: "Internal Server Error",
// 				status: 500,
// 				error: err.message,
// 				response: null,
// 			});
// 		}
// 		res.json({
// 			message: "List Absensi",
// 			status: 200,
// 			error: null,
// 			response: result,
// 		});
// 	});
// });

//MANAGE ABSENSI

app.put("/manage_absensi/:action/:nip", function (req, res) {
	let action = req.params.action;
	let sql, message;

	switch (action) {
		case "approved":
			sql = "UPDATE log_absensi SET status = ? WHERE nip_pegawai = ?";
			message = "Absen approved";
			break;
		case "rejected":
			sql = "UPDATE log_absensi SET status = ? WHERE nip_pegawai = ?";
			message = "Absen rejected";
			break;
		case "telat":
			sql = "UPDATE log_absensi SET status = ? WHERE nip_pegawai = ?";
			message = "Absen marked as telat";
			break;
		case "pulang_awal":
			sql = "UPDATE log_absensi SET status = ? WHERE nip_pegawai = ?";
			message = "Absen marked as pulang awal";
			break;
		default:
			return res.status(400).json({
				message: "Invalid action",
				status: 400,
				error: "Invalid action provided",
				response: null,
			});
	}

	let query = conn.query(sql, [action, req.params.nip], function (err, result) {
		if (err) {
			console.error(`Error performing action ${action}:", err`);
			return res.status(500).json({
				message: "Internal Server Error",
				status: 500,
				error: err.message,
				response: null,
			});
		}
		res.json({
			message: message,
			status: 200,
			error: null,
			response: result,
		});
	});
});

//HISTORY ABSENSI
app.get("/history_absensi", function (req, res) {
	let sql = "SELECT * FROM log_absensi WHERE nip_pegawai = ? ORDER BY id DESC";
	let query = conn.query(sql, [req.session.user.nip], function (err, result) {
		if (err) {
			console.error("Error fetching history absensi:", err);
			return res.status(500).json({
				message: "Internal Server Error",
				status: 500,
				error: err.message,
				response: null,
			});
		}
		res.json({
			message: "History Absensi",
			status: 200,
			error: null,
			response: result,
		});
	});
});

//PENUGASAN OLEH MANAGER

// app.post("/assign_task", function (req, res) {
// 	let nipPetugas = req.body.nip_petugas;
// 	let nipPegawai = req.body.nip_pegawai;

// 	// Prepare the object to be inserted
// 	let insertData = {
// 		nip_petugas: nipPetugas,
// 		nip_pegawai: nipPegawai,
// 	};

// 	// Insert single record into pembagian_tugas table
// 	let sql = "INSERT INTO pembagian_tugas SET ?";
// 	conn.query(sql, insertData, function (err, result) {
// 		if (err) {
// 			console.error("Error assigning task:", err);
// 			return res.status(500).json({
// 				status: 500,
// 				error: "Internal Server Error",
// 				response: null,
// 			});
// 		} else {
// 			console.log("Task assigned successfully");
// 			return res.status(200).json({
// 				message: "Task assigned successfully",
// 				status: 200,
// 				error: null,
// 				response: result,
// 			});
// 		}
// 	});
// });

app.post("/assign_task", function (req, res) {
	let nipPetugas = req.body.nip_petugas;
	let nipPegawaiArray = req.body.nip_pegawai;

	// Prepare an array of objects to be inserted
	let insertData = nipPegawaiArray.map((nip_pegawai) => ({
		nip_petugas: nipPetugas,
		nip_pegawai: nip_pegawai,
	}));

	// Insert multiple records into pembagian_tugas table
	let sql = "INSERT INTO pembagian_tugas (nip_petugas, nip_pegawai) VALUES ?";
	conn.query(
		sql,
		[insertData.map((item) => [item.nip_petugas, item.nip_pegawai])],
		function (err, result) {
			if (err) {
				console.error("Error assigning tasks:", err);
				return res.status(500).json({
					status: 500,
					error: "Internal Server Error",
					response: null,
				});
			} else {
				console.log("Tasks assigned successfully");
				return res.status(200).json({
					message: "Tasks assigned successfully",
					status: 200,
					error: null,
					response: result,
				});
			}
		}
	);
});

//DELETE PENUGASAN OLEH MANAGER

app.delete("/delete_task", function (req, res) {
	let nipPetugas = req.body.nip_petugas;

	// Delete rows from pembagian_tugas table where nip_petugas matches
	let sql = "DELETE FROM pembagian_tugas WHERE nip_petugas = ?";
	conn.query(sql, [nipPetugas], function (err, result) {
		if (err) {
			console.error("Error deleting tasks:", err);
			return res.status(500).json({
				status: 500,
				error: "Internal Server Error",
				response: null,
			});
		} else {
			console.log("Tasks deleted successfully");
			return res.status(200).json({
				message: "Tasks deleted successfully",
				status: 200,
				error: null,
				response: result, // Ensure 'result' is properly formatted or transformed as needed
			});
		}
	});
});

//CHANGE PETUGAS
app.put("/change_petugas", function (req, res) {
	let nipPegawai = req.body.nip_pegawai;
	let nipPetugas = req.body.nip_petugas;

	let sql = "UPDATE pembagian_tugas SET nip_petugas = ? WHERE nip_pegawai = ?;";
	let query = conn.query(sql, [nipPetugas, nipPegawai], function (err, result) {
		if (err) {
			console.error("Error changing petugas:", err);
			return res.status(500).json({
				message: "Internal Server Error",
				status: 500,
				error: err.message,
				response: null,
			});
		}
		res.json({
			message: "Petugas changed",
			status: 200,
			error: null,
			response: result,
		});
	});
});

//LOGOUT

app.post("/logout", function (req, res) {
	// Destroy session
	req.session.destroy(function (err) {
		if (err) {
			console.error("Error destroying session:", err);
			return res.status(500).json({
				status: 500,
				error: "Internal Server Error",
				response: null,
			});
		}

		console.log("Session destroyed");
		return res.status(200).json({
			message: "Session destroyed",
			status: 200,
			error: null,
			response: null,
		});
	});
});

var server = app.listen(8000, function () {
	console.log("API Server running at port 8000");
});
