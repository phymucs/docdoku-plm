/*global casper*/

var domain = casper.cli.get("domain");
var port = casper.cli.get("port");
var login = casper.cli.get("login");
var pass = casper.cli.get("pass");
var workspace = casper.cli.get("workspace");
var contextPath = casper.cli.get("contextPath");

var homeUrl = 'http://'+domain+':'+port + contextPath;

var documents = {
	template1 : {
		number : 'CasperJsTestDocumentTemplate'
	},
	folder1: 'CasperJsTestFolder',
	document1 : {
		number : '000-AAA-CasperJsTestDocument'
	}
};

var products = {
	template1 : {
		number : 'CasperJsTestPartTemplate'
	},
	part1 : {
		number : '000-AAA-CasperJsTestPart',
		name : 'CasperJsTestPart'
	},
	product1: {
		number : '000-AAA-CasperJsTestProduct',
		name : 'CasperJsTestProduct'
	}
};

var workflows = {
	role1 : 'CasperJsRole1',
	role2 : 'CasperJsRole2',
	role3 : 'CasperJsRole3'
};

var changeItems = {
	changeIssue1 : {
		number : 'CasperJsTestIssue'
	}
};

var urls = {
	productManagement : homeUrl+'/product-management/#'+workspace,
	documentManagement : homeUrl+'/document-management/#'+workspace,
	changeManagement : homeUrl+'/change-management/#'+workspace
};

var apiUrls = {
	userInfo : homeUrl+'/api/workspaces/'+workspace+'/users/me',
	deletePart : homeUrl+'/api/workspaces/'+workspace+'/parts/'+products.part1.number+'-A',
	deleteDocument : homeUrl+'/api/workspaces/'+workspace+'/documents/'+documents.document1.number+'-A',
	deleteProduct : homeUrl+'/api/workspaces/'+workspace+'/products/'+products.product1.number,
	deleteFolder : homeUrl+'/api/workspaces/'+workspace+'/folders/'+workspace+":"+documents.folder1
};